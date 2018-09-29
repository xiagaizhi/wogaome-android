/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yushi.leke.uamp.playback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.google.android.exoplayer2.ExoPlayer;
import com.yufan.library.manager.DialogManager;
import com.yushi.leke.uamp.MusicService;
import com.yushi.leke.uamp.model.MusicProvider;
import com.yushi.leke.uamp.model.MutableMediaMetadata;
import com.yushi.leke.uamp.utils.LogHelper;
import com.yushi.leke.uamp.utils.MediaIDHelper;

import static android.support.v4.media.session.MediaSessionCompat.QueueItem;

/**
 * A class that implements local media playback using {@link
 * }
 * vod播放器交互的实现
 */
public final class VodPlayback implements Playback {

    private static final String TAG = LogHelper.makeLogTag(VodPlayback.class);
    //目前支持的几种播放方式
    private AliyunPlayAuth mAliyunPlayAuth;
    private AliyunLocalSource mAliyunLocalSource;
    private AliyunVidSts mAliyunVidSts;

    // The volume we set the media player to when we lose audio focus, but are
    // allowed to reduce the volume instead of stopping playback.
    //当音频失去焦点，且不需要停止播放，只需要减小音量时，我们设置的媒体播放器音量大小
    //例如微信的提示音响起，我们只需要减小当前音乐的播放音量即可
    public static final int VOLUME_DUCK = 10;
    // The volume we set the media player when we have audio focus.
    //当我们获取音频焦点时设置的播放音量大小
    public static final int VOLUME_NORMAL = 40;

    // we don't have audio focus, and can't duck (play at a low volume)
    //没有获取到音频焦点，也不允许duck状态
    private static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    // we don't have focus, but can duck (play at a low volume)
    //没有获取到音频焦点，但允许duck状态
    private static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    // we have full audio focus
    //完全获取音频焦点
    private static final int AUDIO_FOCUSED = 2;

    private final Context mContext;
    private final WifiManager.WifiLock mWifiLock;
    private boolean mPlayOnFocusGain;
    private Callback mCallback;
    private final MusicProvider mMusicProvider;
    private boolean mAudioNoisyReceiverRegistered;
    private String mCurrentMediaId;

    //当前音频焦点的状态
    private int mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
    private final AudioManager mAudioManager;
//    //private SimpleExoPlayer mExoPlayer;
//    private final ExoPlayerEventListener mEventListener = new ExoPlayerEventListener();

    private AliyunVodPlayer mAliyunVodPlayer;
    private final IntentFilter mAudioNoisyIntentFilter =
            new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

    /**
     * 接收耳机插拔的广播
     */
    private final BroadcastReceiver mAudioNoisyReceiver =
            new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                        LogHelper.d(TAG, "Headphones disconnected.");
                        //当音乐正在播放中，通知Service暂停播放音乐（在Service.onStartCommand中处理此命令）
                        if (isPlaying()) {
                            Intent i = new Intent(context, MusicService.class);
                            i.setAction(MusicService.ACTION_CMD);
                            i.putExtra(MusicService.CMD_NAME, MusicService.CMD_PAUSE);
                            mContext.startService(i);
                        }
                    }
                }
            };

    public VodPlayback(Context context, MusicProvider musicProvider) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mMusicProvider = musicProvider;

        this.mAudioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
        // Create the Wifi lock (this does not acquire the lock, this just creates it)
        this.mWifiLock = ((WifiManager) applicationContext.getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "uAmp_lock");
    }

    @Override
    public void start() {
        // Nothing to do
    }

    @Override
    public void stop(boolean notifyListeners) {
        giveUpAudioFocus();//放弃音频焦点
        unregisterAudioNoisyReceiver();//取消监听耳机插播广播
        releaseResources(true);//释放资源
    }

    @Override
    public void setState(int state) {

        // Nothing to do (mExoPlayer holds its own state).
    }

    /**
     * Player.STATE_IDLE 当前播放器没有可播放的媒体内容
     * Player.STATE_BUFFERING 播放器无法立即从当前位置开始播放，一般在需要加载更多数据时出现此状态
     * Player.STATE_READY 播放器可以立即从当前位置开始播放，此时可以使用getPlayWhenReady查询是否正在播放音乐
     * Player.STATE_ENDED 播放器播放已结束
     *
     * @return
     */
    @Override
    public int getState() {
        if (mAliyunVodPlayer == null) {
            return PlaybackStateCompat.STATE_NONE;
        }
        switch (mAliyunVodPlayer.getPlayerState()) {
            case Idle:
                return PlaybackStateCompat.STATE_PAUSED;
            case SeekLive:
                return PlaybackStateCompat.STATE_BUFFERING;//缓冲
            case Prepared:
            case Replay:
            case Started:
            case ChangeQuality:
            case Released:
                return mAliyunVodPlayer.isPlaying()
                        ? PlaybackStateCompat.STATE_PLAYING
                        : PlaybackStateCompat.STATE_PAUSED;
            case Paused:
                return PlaybackStateCompat.STATE_PAUSED;
            case Stopped:
            case Completed:
                return PlaybackStateCompat.STATE_PAUSED;
            case Error:
                return PlaybackStateCompat.STATE_ERROR;
            default:
                return PlaybackStateCompat.STATE_NONE;
        }
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public boolean isPlaying() {
        return mPlayOnFocusGain || (mAliyunVodPlayer != null && mAliyunVodPlayer.isPlaying());
    }

    /**
     * 清空之前设置的播放源
     */
    private void clearAllSource() {
        mAliyunPlayAuth = null;
        mAliyunVidSts = null;
        mAliyunLocalSource = null;
    }

    /**
     * 准备vidsts 源
     *
     * @param vidSts
     */
    public void prepareVidsts(AliyunVidSts vidSts) {
        clearAllSource();
        mAliyunVodPlayer.prepareAsync(vidSts);
    }

    @Override
    public long getCurrentStreamPosition() {
        return mAliyunVodPlayer != null ? mAliyunVodPlayer.getCurrentPosition() : 0;
    }

    @Override
    public void updateLastKnownStreamPosition() {
        // Nothing to do. Position maintained by ExoPlayer.
    }

    @Override
    public void play(QueueItem item) {

        mPlayOnFocusGain = true;
        tryToGetAudioFocus();
        registerAudioNoisyReceiver();
        String mediaId = item.getDescription().getMediaId();
        boolean mediaHasChanged = !TextUtils.equals(mediaId, mCurrentMediaId);
        if (mediaHasChanged) {
            mCurrentMediaId = mediaId;
        }
        int listenable = item.getDescription().getExtras().getInt(MutableMediaMetadata.listenable);//是否试听0否,1是
        int levelStatus = item.getDescription().getExtras().getInt(MutableMediaMetadata.levelStatus);//等级(0代表已解锁,其他代表未解锁)
        if (listenable == 1 || levelStatus == 0) {
            if (mediaHasChanged || mAliyunVodPlayer == null) {
                releaseResources(false); // release everything except the player

                if (mAliyunVodPlayer == null) {
                    mAliyunVodPlayer = new AliyunVodPlayer(mContext);
                    mAliyunVodPlayer.setAutoPlay(true);
                    mAliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion() {
                            if (mCallback != null) {
                                mCallback.onCompletion();
                            }
                        }
                    });
                    mAliyunVodPlayer.setOnAutoPlayListener(new IAliyunVodPlayer.OnAutoPlayListener() {
                        @Override
                        public void onAutoPlayStarted() {
//                        mAliyunVodPlayer.setVolume(mAliyunVodPlayer.getVolume());
                        }
                    });

                    //播放信息监听
                    mAliyunVodPlayer.setOnInfoListener(new IAliyunVodPlayer.OnInfoListener() {
                        @Override
                        public void onInfo(int arg0, int arg1) {
                            if (mCallback != null) {
                                mCallback.onPlaybackStatusChanged(getState());
                            }
                        }
                    });
                    mAliyunVodPlayer.setOnStoppedListner(new IAliyunVodPlayer.OnStoppedListener() {
                        @Override
                        public void onStopped() {
                            if (mCallback != null) {
                                mCallback.onPlaybackStatusChanged(getState());
                            }
                        }
                    });
                    mAliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
                        @Override
                        public void onError(int i, int i1, String s) {
                            if (mCallback != null) {
                                mCallback.onError("ExoPlayer error " + s);
                            }
                        }
                    });

                }
                AliyunVidSts aliyunVidSts = new AliyunVidSts();
                String aliVideoId = item.getDescription().getExtras().getString(MutableMediaMetadata.videoId);
                if (!TextUtils.isEmpty(aliVideoId)) {
                    aliyunVidSts.setVid(aliVideoId);
                    if (MusicProvider.getInstance().getAliyunAuth() != null) {
                        aliyunVidSts.setAcId(MusicProvider.getInstance().getAliyunAuth().getAccessKeyId());
                        aliyunVidSts.setAkSceret(MusicProvider.getInstance().getAliyunAuth().getAccessKeySecret());
                        aliyunVidSts.setSecurityToken(MusicProvider.getInstance().getAliyunAuth().getSecurityToken());
                    }
                }
                prepareVidsts(aliyunVidSts);
                // If we are streaming from the internet, we want to hold a
                // Wifi lock, which prevents the Wifi radio from going to
                // sleep while the song is playing.
                //获取wifi锁
                mWifiLock.acquire();
            }

            configurePlayerState();
            if (mCallback != null) {
                mCallback.onPlaybackStatusChanged(getState());
            }
        } else {
            DialogManager.getInstance().toast("音频暂未解锁");
            pause();
        }
    }

    @Override
    public void pause() {
        // Pause player and cancel the 'foreground service' state.
        if (mAliyunVodPlayer != null) {
            mAliyunVodPlayer.pause();
        }
        // While paused, retain the player instance, but give up audio focus.
        releaseResources(false);
        unregisterAudioNoisyReceiver();
        if (mCallback != null) {
            mCallback.onPlaybackStatusChanged(getState());
        }
    }

    @Override
    public void seekTo(long position) {
        LogHelper.d(TAG, "seekTo called with ", position);
        if (mAliyunVodPlayer != null) {
            registerAudioNoisyReceiver();
            mAliyunVodPlayer.seekTo((int) position);

        }
    }

    @Override
    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    @Override
    public void setCurrentMediaId(String mediaId) {
        this.mCurrentMediaId = mediaId;
    }

    @Override
    public String getCurrentMediaId() {
        return mCurrentMediaId;
    }

    /**
     * 尝试获取音频焦点
     * requestAudioFocus(OnAudioFocusChangeListener l, int streamType, int durationHint)
     * OnAudioFocusChangeListener l：音频焦点状态监听器
     * int streamType：请求焦点的音频类型
     * int durationHint：请求焦点音频持续性的指示
     * AUDIOFOCUS_GAIN：指示申请得到的音频焦点不知道会持续多久，一般是长期占有
     * AUDIOFOCUS_GAIN_TRANSIENT：指示要申请的音频焦点是暂时性的，会很快用完释放的
     * AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK：指示要申请的音频焦点是暂时性的，同时还指示当前正在使用焦点的音频可以继续播放，只是要“duck”一下（降低音量）
     */
    private void tryToGetAudioFocus() {
        LogHelper.d(TAG, "tryToGetAudioFocus");
        int result =
                mAudioManager.requestAudioFocus(
                        mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mCurrentAudioFocusState = AUDIO_FOCUSED;
        } else {
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
        }
    }

    /**
     * 放弃音频焦点
     */
    private void giveUpAudioFocus() {
        LogHelper.d(TAG, "giveUpAudioFocus");
        //申请放弃音频焦点
        if (mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener)
                == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //AudioManager.AUDIOFOCUS_REQUEST_GRANTED 申请成功
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
        }
    }

    /**
     * Reconfigures the player according to audio focus settings and starts/restarts it. This method
     * starts/restarts the ExoPlayer instance respecting the current audio focus state. So if we
     * have focus, it will play normally; if we don't have focus, it will either leave the player
     * paused or set it to a low volume, depending on what is permitted by the current focus
     * settings.
     * 根据音频焦点的设置重新配置播放器 以及 启动/重新启动 播放器。调用这个方法 启动/重新启动 播放器实例取决于当前音频焦点的状态。
     * 因此如果我们持有音频焦点，则正常播放音频；如果我们失去音频焦点，播放器将暂停播放或者设置为低音量，这取决于当前焦点设置允许哪种设置
     */
    private void configurePlayerState() {
        LogHelper.d(TAG, "configurePlayerState. mCurrentAudioFocusState=", mCurrentAudioFocusState);
        if (mCurrentAudioFocusState == AUDIO_NO_FOCUS_NO_DUCK) {
            // We don't have audio focus and can't duck, so we have to pause
            pause();
        } else {
            registerAudioNoisyReceiver();

            if (mCurrentAudioFocusState == AUDIO_NO_FOCUS_CAN_DUCK) {
                // We're permitted to play, but only if we 'duck', ie: play softly
                mAliyunVodPlayer.setVolume(VOLUME_DUCK);
            } else {
                if (mAliyunVodPlayer.getVolume() <= 0) {
                    mAliyunVodPlayer.setVolume(VOLUME_NORMAL);
                }
            }

            // If we were playing when we lost focus, we need to resume playing.
            if (mPlayOnFocusGain) {
                //播放的过程中因失去焦点而暂停播放，短暂暂停之后仍需要继续播放时会进入这里执行相应的操作
                mAliyunVodPlayer.start();
                mPlayOnFocusGain = false;
            }
        }
    }

    /**
     * 请求音频焦点成功之后监听其状态的Listener
     * AUDIOFOCUS_GAIN 得到音频焦点时触发的状态，请求得到的音频焦点一般会长期占有
     * AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK 失去音频焦点时触发的状态，在该状态的时候不需要暂停音频，但是我们需要降低音频的声音
     * AUDIOFOCUS_LOSS_TRANSIENT 失去音频焦点时触发的状态，但是该状态不会长时间保持，此时我们应该暂停音频，且当重新获取音频焦点的时候继续播放
     * AUDIOFOCUS_LOSS 失去音频焦点时触发的状态，且这个状态有可能会长期保持，此时应当暂停音频并释放音频相关的资源
     */
    private final AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    LogHelper.d(TAG, "onAudioFocusChange. focusChange=", focusChange);
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            mCurrentAudioFocusState = AUDIO_FOCUSED;
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            // Audio focus was lost, but it's possible to duck (i.e.: play quietly)
                            mCurrentAudioFocusState = AUDIO_NO_FOCUS_CAN_DUCK;
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                            // Lost audio focus, but will gain it back (shortly), so note whether
                            // playback should resume
                            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
                            mPlayOnFocusGain = mAliyunVodPlayer != null && mAliyunVodPlayer.isPlaying();
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                            // Lost audio focus, probably "permanently"
                            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK;
                            break;
                    }

                    if (mAliyunVodPlayer != null) {
                        // Update the player state based on the change
                        configurePlayerState();
                    }
                }
            };

    /**
     * Releases resources used by the service for playback, which is mostly just the WiFi lock for
     * local playback. If requested, the ExoPlayer instance is also released.
     * 释放Service用于播放的资源，这个资源主要是指本地播放时的wifi锁。
     * 如有必要，也可以将ExoPlayer实例的资源也释放掉（由传参releasePlayer决定）
     *
     * @param releasePlayer 指示播放器是否应该被释放
     */
    private void releaseResources(boolean releasePlayer) {
        LogHelper.d(TAG, "releaseResources. releasePlayer=", releasePlayer);

        // Stops and releases player (if requested and available).
        if (releasePlayer && mAliyunVodPlayer != null) {
            mAliyunVodPlayer.release();
            mAliyunVodPlayer.reset();
            mAliyunVodPlayer = null;
            mPlayOnFocusGain = false;
        }

        if (mWifiLock.isHeld()) {
            mWifiLock.release();
        }
    }

    private void registerAudioNoisyReceiver() {
        //注册耳机插拔的广播接收者
        if (!mAudioNoisyReceiverRegistered) {
            mContext.registerReceiver(mAudioNoisyReceiver, mAudioNoisyIntentFilter);
            mAudioNoisyReceiverRegistered = true;
        }
    }

    private void unregisterAudioNoisyReceiver() {
        //注销耳机插拔、蓝牙耳机断连的广播接收者
        if (mAudioNoisyReceiverRegistered) {
            mContext.unregisterReceiver(mAudioNoisyReceiver);
            mAudioNoisyReceiverRegistered = false;
        }
    }


}
