package com.yushi.leke.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.socialize.UMShareAPI;
import com.yufan.library.Global;
import com.yufan.library.base.BaseActivity;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.SPManager;
import com.yufan.library.manager.UserManager;
import com.yufan.library.util.FileUtil;
import com.yufan.library.util.PxUtil;
import com.yushi.leke.R;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.login.LoginFragment;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.fragment.splash.SplashFragment;
import com.yushi.leke.uamp.MusicService;
import com.yushi.leke.uamp.model.MusicProvider;
import com.yushi.leke.uamp.ui.MediaBrowserProvider;
import com.yushi.leke.uamp.utils.LogHelper;
import com.yushi.leke.uamp.utils.MediaIDHelper;
import com.yushi.leke.uamp.utils.ResourceHelper;
import com.yushi.leke.util.AliDotId;
import com.yushi.leke.util.ArgsUtil;
import com.yushi.leke.util.AudioTimerUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity implements MediaBrowserProvider {
    private String TAG = "MainActivity";
    public static final String EXTRA_START_FULLSCREEN =
            "com.yushi.leke.uamp.EXTRA_START_FULLSCREEN";
    public static final String EXTRA_CURRENT_MEDIA_DESCRIPTION =
            "com.yushi.leke.uamp.CURRENT_MEDIA_DESCRIPTION";
    private MediaBrowserCompat mMediaBrowser;
    private List<IActivityResult> results = new ArrayList<>();

    private String mediaId;
    private String ablumId;

    public void registerIActivityResult(IActivityResult callBack) {
        if (!results.contains(callBack)) {
            results.add(callBack);
        }
    }

    public void unregisterIActivityResult(IActivityResult callBack) {
        if (results.contains(callBack)) {
            results.remove(callBack);
        }
    }


    public ImageView getMusicView() {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams rightLayoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(rightLayoutParams);


        imageView.setPadding(0, 0, PxUtil.convertDIP2PX(this, 18), 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicPlayerActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        return imageView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            // Since our app icon has the same color as colorPrimary, our entry in the Recent Apps
            // list gets weird. We need to change either the icon or the color
            // of the TaskDescription.
            ActivityManager.TaskDescription taskDesc = new ActivityManager.TaskDescription(
                    getTitle().toString(),
                    BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_white),
                    ResourceHelper.getThemeColor(this, R.attr.colorPrimary,
                            android.R.color.darker_gray));
            setTaskDescription(taskDesc);
        }

        //检查Activity重新创建时是否需要开启全屏播放器界面
        // Only check if a full screen player is needed on the first time:
        if (savedInstanceState == null) {
            startFullScreenActivityIfNeeded(getIntent());
        }
        // Connect a media browser just to get the media session token. There are other ways
        // this can be done, for example by sharing the session token directly.
        //创建媒体浏览客户端（MediaBrowserCompat）
        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, MusicService.class), mConnectionCallback, null);
        FileUtil.initFileAccess();
        if (!TextUtils.isEmpty(UserManager.getInstance().getToken())) {
            loadRootFragment(R.id.activity_content_level0, UIHelper.creat(MainFragment.class).build());//.put(Global.BUNDLE_KEY_BROWSER_URL,"http://www.baidu.com")
        } else {
            loadRootFragment(R.id.activity_content_level0, UIHelper.creat(LoginFragment.class).build());
        }
        ablumId = SPManager.getInstance().getString(Global.SP_KEY_ALBUM_ID, "");
        mediaId = SPManager.getInstance().getString(Global.SP_KEY_MEDIA_ID, "");
        AudioTimerUtil.getInstance().accumulate();
        //app启动数据埋点
        Map<String, String> params = new HashMap<>();
        params.put("uid", UserManager.getInstance().getUid());
        ArgsUtil.getInstance().datapoint(AliDotId.id_0100, params);
    }

    private void startFullScreenActivityIfNeeded(Intent intent) {
        if (intent != null && intent.getBooleanExtra(EXTRA_START_FULLSCREEN, false)) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(lp);
            getWindow().setBackgroundDrawableResource(com.yufan.library.R.color.white);
            Intent fullScreenIntent = new Intent(this, MusicPlayerActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra(EXTRA_CURRENT_MEDIA_DESCRIPTION,
                            intent.getParcelableExtra(EXTRA_CURRENT_MEDIA_DESCRIPTION));
            startActivity(fullScreenIntent);
        } else {
            UIHelper.openFragment(this, UIHelper.creat(SplashFragment.class).build(), false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogHelper.d(TAG, "Activity onStart");
        mMediaBrowser.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogHelper.d(TAG, "Activity onStop");
        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(this);
        if (controllerCompat != null) {
            controllerCompat.unregisterCallback(mMediaControllerCallback);
        }
        if (mMediaBrowser != null && mMediaBrowser.isConnected() && !TextUtils.isEmpty(ablumId)) {
            mMediaBrowser.unsubscribe(ablumId);
        }
        mMediaBrowser.disconnect();
    }

    /**
     * Check if the MediaSession is active and in a "playback-able" state
     * (not NONE and not STOPPED).
     *
     * @return true if the MediaSession's state requires playback controls to be visible.
     */
    protected boolean shouldShowControls() {
        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(this);
        if (mediaController == null ||
                mediaController.getMetadata() == null ||
                mediaController.getPlaybackState() == null) {
            return false;
        }
        switch (mediaController.getPlaybackState().getState()) {
            case PlaybackStateCompat.STATE_ERROR:
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:
                return false;
            default:
                return true;
        }
    }

    @Override
    public MediaBrowserCompat getMediaBrowser() {
        return mMediaBrowser;
    }

    @Override
    public void onMediaItemSelected(MediaBrowserCompat.MediaItem item) {
        LogHelper.d(TAG, "onMediaItemSelected, mediaId=" + item.getMediaId());
        MusicProvider.getInstance().updateProvider();
        if (item.isPlayable()) {//可播放状态
            if (MediaIDHelper.isMediaItemPlaying(this, item)) {
                PlaybackStateCompat state = MediaControllerCompat.getMediaController(this).getPlaybackState();
                if (state != null) {
                    MediaControllerCompat.TransportControls controls = MediaControllerCompat.getMediaController(this).getTransportControls();
                    switch (state.getState()) {
                        case PlaybackStateCompat.STATE_PLAYING: // fall through
                        case PlaybackStateCompat.STATE_BUFFERING:
                            controls.pause();
                            break;
                        case PlaybackStateCompat.STATE_PAUSED:
                        case PlaybackStateCompat.STATE_STOPPED:
                            controls.play();
                            break;
                        default:
                            controls.play();
                            break;
                    }
                }
            } else {
                MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls()
                        .playFromMediaId(item.getMediaId(), null);
            }
        } else {
            LogHelper.w(TAG, "Ignoring MediaItem that is neither browsable nor playable: ",
                    "mediaId=", item.getMediaId());
        }
    }


    /**
     * @param token
     * @throws RemoteException
     */
    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(this, token);
        MediaControllerCompat.setMediaController(this, mediaController);
        mediaController.registerCallback(mMediaControllerCallback);


    }

    // Callback that ensures that we are showing the controls
    /**
     * 媒体控制器控制播放过程中的回调接口
     * 这里主要是根据当前播放的状态决定PlaybackControlsFragment是否显示
     */
    private final MediaControllerCompat.Callback mMediaControllerCallback =
            new MediaControllerCompat.Callback() {
                @Override
                public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {

                }

                @Override
                public void onMetadataChanged(MediaMetadataCompat metadata) {

                }
            };


    /**
     * 连接媒体浏览服务成功后的回调接口
     */
    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    LogHelper.d(TAG, "onConnected");
                    try {
                        connectToSession(mMediaBrowser.getSessionToken());

                        if (MusicProvider.getInstance().getMusics().size() == 0 && !TextUtils.isEmpty(ablumId)) {
                            mMediaBrowser.unsubscribe(ablumId);
                            mMediaBrowser.subscribe(ablumId, new MediaBrowserCompat.SubscriptionCallback() {
                                @Override
                                public void onChildrenLoaded(@NonNull String parentId,
                                                             @NonNull List<MediaBrowserCompat.MediaItem> children) {
                                    try {
                                        LogHelper.d(TAG, "fragment onChildrenLoaded, parentId=" + parentId +
                                                "  count=" + children.size());
                                        MusicProvider.getInstance().updateProvider();
                                        if (!TextUtils.isEmpty(mediaId)) {
                                            MediaControllerCompat.getMediaController(MainActivity.this).getTransportControls()
                                                    .prepareFromMediaId(mediaId, null);

                                        }
                                    } catch (Throwable t) {
                                        LogHelper.e(TAG, "Error on childrenloaded", t);
                                    }
                                }
                            });

                        }
                    } catch (RemoteException e) {
                        LogHelper.e(TAG, e, "could not connect media controller");

                    }
                }
            };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (int i = 0; i < results.size(); i++) {
            IActivityResult result = results.get(i);
            if (result != null) {
                result.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    public interface IActivityResult {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        XGPushClickedResult click = XGPushManager.onActivityStarted(this);
        // click.getCustomContent()
        Log.d("TPush", "onResumeXGPushClickedResult:" + click);
        if (click != null) { // 判断是否来自信鸽的打开方式
            Toast.makeText(this, "通知被点击:" + click.toString(),
                    Toast.LENGTH_SHORT).show();
            try {
                JSONObject obj = new JSONObject(click.getCustomContent());
                // key1为前台配置的key
                if (!obj.isNull("qaq")) {
                    Log.d("LOGH", "nonull!!!" + obj.getString("qaq"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        XGPushManager.onActivityStoped(this);

    }


}
