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

package com.yushi.leke.uamp.model;

import android.graphics.Bitmap;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.album.audioList.AlbumAudio;
import com.yushi.leke.uamp.utils.LogHelper;
import com.yushi.leke.uamp.utils.MediaIDHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.yushi.leke.uamp.utils.MediaIDHelper.MEDIA_ID_MUSICS_BY_GENRE;


/**
 * Simple data provider for music tracks. The actual metadata source is delegated to a
 * MusicProviderSource defined by a constructor argument of this class.
 * MusicProvider：简单的音乐数据提供者
 */
public class MusicProvider {

    private static final String TAG = LogHelper.makeLogTag(MusicProvider.class);


    private final ConcurrentMap<String, MutableMediaMetadata> mMusicListById;
    private static MusicProvider musicProvider;
    private final Set<String> mFavoriteTracks;
    private AliyunAuth aliyunAuth;
    private final Timer timer;
    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            getAuth();
        }
    };

    private void getAuth() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getAuth()).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                aliyunAuth = JSON.parseObject(mApiBean.data, AliyunAuth.class);
            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    public AliyunAuth getAliyunAuth() {
        return aliyunAuth;
    }

    enum State {
        NON_INITIALIZED, INITIALIZING, INITIALIZED
    }

    private volatile State mCurrentState = State.NON_INITIALIZED;

    public interface Callback {
        void onMusicCatalogReady(boolean success);
    }


    public static MusicProvider getInstance() {
        if (musicProvider == null) {
            musicProvider = new MusicProvider();
        }
        return musicProvider;
    }


    public void recycleTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public MusicProvider() {
        mMusicListById = new ConcurrentHashMap<>();
        mFavoriteTracks = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());//map change to set
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 0, 30 * 60 * 1000);


    }


    /**
     * Get music tracks of the given genre
     */
    public Collection<MutableMediaMetadata> getMusics() {
        if (mCurrentState != State.INITIALIZED) {
            return Collections.emptyList();
        }
        return mMusicListById.values();
    }


    /**
     * Return the MediaMetadataCompat for the given musicID.
     *
     * @param musicId The unique, non-hierarchical music ID.
     */
    public MediaMetadataCompat getMusic(String musicId) {
        return mMusicListById.containsKey(musicId) ? mMusicListById.get(musicId).metadata : null;
    }

    public synchronized void updateMusicArt(String musicId, Bitmap albumArt, Bitmap icon) {
        MediaMetadataCompat metadata = getMusic(musicId);
        metadata = new MediaMetadataCompat.Builder(metadata)

                // set high resolution bitmap in METADATA_KEY_ALBUM_ART. This is used, for
                // example, on the lockscreen background when the media session is active.
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt)

                // set small version of the album art in the DISPLAY_ICON. This is used on
                // the MediaDescription and thus it should be small to be serialized if
                // necessary
                .putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, icon)

                .build();

        MutableMediaMetadata mutableMetadata = mMusicListById.get(musicId);
        if (mutableMetadata == null) {
            throw new IllegalStateException("Unexpected error: Inconsistent data structures in " +
                    "MusicProvider");
        }

        mutableMetadata.metadata = metadata;
    }

    public void setFavorite(String musicId, boolean favorite) {
        if (favorite) {
            mFavoriteTracks.add(musicId);
        } else {
            mFavoriteTracks.remove(musicId);
        }
    }

    public boolean isInitialized() {
        return mCurrentState == State.INITIALIZED;
    }

    /**
     * 判断该音乐是否在"喜欢"列表中
     *
     * @param musicId
     * @return
     */
    public boolean isFavorite(String musicId) {
        return mFavoriteTracks.contains(musicId);
    }

    /**
     * Get the list of music tracks from a server and caches the track information
     * for future reference, keying tracks by musicId and grouping by genre.
     * 从服务端获取音乐路径列表，以及缓存列表数据以便将来直接引用
     * 使用musicId作为列表的关键字并将音乐按类型分组
     */
    public void retrieveMediaAsync(String parentMediaId, final Callback callback) {
        LogHelper.d(TAG, "retrieveMediaAsync called");
//        if (mCurrentState == State.INITIALIZED) {
//            if (callback != null) {
//                // Nothing to do, execute callback immediately
//                callback.onMusicCatalogReady(true);
//            }
//            return;
//        }
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getPlayList(parentMediaId)).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                JSONObject jsonObject = JSON.parseObject(mApiBean.data);
                String listStr = jsonObject.getString("audioViewInfoList");
                int levelStatus = jsonObject.getInteger("levelStatus");
                List<AlbumAudio> albumAudios = JSON.parseArray(listStr, AlbumAudio.class);
                mMusicListById.clear();
                for (int i = 0; i < albumAudios.size(); i++) {
                    AlbumAudio albumAudio = albumAudios.get(i);
                    MediaMetadataCompat item = new MediaMetadataCompat.Builder()
                            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, albumAudio.getAudioId() + "")
                            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, albumAudio.getAlbumId() + "")
                            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "作者")
                            .putString(MediaMetadataCompat.METADATA_KEY_GENRE,albumAudio.getAlbumId()+"")
                            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, "")
                            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, albumAudio.getAudioName())
                            .putLong(MediaMetadataCompat.METADATA_KEY_BT_FOLDER_TYPE, 1)
                            .build();

                    item.getDescription().getExtras().putInt(MediaMetadataCompat.METADATA_KEY_DURATION, albumAudio.getDuration());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.baseCount, albumAudio.getBaseCount());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.audioStatus, albumAudio.getAudioStatus());
                    item.getDescription().getExtras().putLong(MutableMediaMetadata.ctime, albumAudio.getCtime());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.deleted, albumAudio.getDeleted());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.listenable, albumAudio.getListenable());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.size, albumAudio.getSize());
                    item.getDescription().getExtras().putLong(MutableMediaMetadata.utime, albumAudio.getUtime());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.viewPeople, albumAudio.getViewPeople());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.viewTimes, albumAudio.getViewTimes());
                    item.getDescription().getExtras().putInt(MutableMediaMetadata.levelStatus, levelStatus);
                    item.getDescription().getExtras().putString(MutableMediaMetadata.videoId, albumAudio.getAliVideoId());
                    String musicId = item.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID);
                    mMusicListById.put(musicId, new MutableMediaMetadata(musicId, item));
                }
                mCurrentState = State.INITIALIZED;
                if (callback != null) {
                    callback.onMusicCatalogReady(true);
                }

            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {

            }
        });

    }


    public List<MediaBrowserCompat.MediaItem> getChildren() {
        List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();
        for (MutableMediaMetadata metadata : getMusics()) {
            mediaItems.add(createMediaItem(metadata));
        }
        return mediaItems;
    }


    private MediaBrowserCompat.MediaItem createMediaItem(MutableMediaMetadata metadata) {
        // Since mediaMetadata fields are immutable, we need to create a copy, so we
        // can set a hierarchy-aware mediaID. We will need to know the media hierarchy
        // when we get a onPlayFromMusicID call, so we can create the proper queue based
        // on where the music was selected from (by artist, by genre, random, etc)
        //我们可以基于在音乐类型的选择（由艺术家、流派、随机、等）构建适当的音乐队列
        String genre = metadata.metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE);
        String hierarchyAwareMediaID = MediaIDHelper.createMediaID(
                metadata.metadata.getDescription().getMediaId(), MEDIA_ID_MUSICS_BY_GENRE, genre);
        MediaMetadataCompat copy = new MediaMetadataCompat.Builder(metadata.metadata)
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, hierarchyAwareMediaID)
                .build();
        copy.getDescription().getExtras().putInt(MediaMetadataCompat.METADATA_KEY_DURATION, metadata.metadata.getDescription().getExtras().getInt(MediaMetadataCompat.METADATA_KEY_DURATION));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.baseCount, metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.baseCount));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.audioStatus, metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.audioStatus));
        copy.getDescription().getExtras().putLong(MutableMediaMetadata.ctime, metadata.metadata.getDescription().getExtras().getLong(MutableMediaMetadata.ctime));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.deleted, metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.deleted));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.listenable, metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.listenable));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.size, metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.size));
        copy.getDescription().getExtras().putLong(MutableMediaMetadata.utime, metadata.metadata.getDescription().getExtras().getLong(MutableMediaMetadata.utime));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.viewPeople,metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.viewPeople));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.viewTimes, metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.viewTimes));
        copy.getDescription().getExtras().putInt(MutableMediaMetadata.levelStatus, metadata.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.levelStatus));
        copy.getDescription().getExtras().putString(MutableMediaMetadata.videoId, metadata.metadata.getDescription().getExtras().getString(MutableMediaMetadata.videoId));
        return new MediaBrowserCompat.MediaItem(copy.getDescription(), MediaBrowserCompat.MediaItem.FLAG_PLAYABLE);

    }

}
