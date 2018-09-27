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

package com.yushi.leke.uamp.utils;

import android.app.Activity;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;


import com.yushi.leke.uamp.model.MusicProvider;
import com.yushi.leke.uamp.model.MutableMediaMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Utility class to help on queue related tasks.
 */
public class QueueHelper {

    private static final String TAG =LogHelper.makeLogTag(QueueHelper.class);

    private static final int RANDOM_QUEUE_SIZE = 10;

    public static List<MediaSessionCompat.QueueItem> getPlayingQueue(String mediaId,
            MusicProvider musicProvider) {

        // extract the browsing hierarchy from the media ID:
        String[] hierarchy = MediaIDHelper.getHierarchy(mediaId);

        if (hierarchy.length != 2) {
            LogHelper.e(TAG, "Could not build a playing queue for this mediaId: ", mediaId);
            return null;
        }

        String categoryType = hierarchy[0];
        String categoryValue = hierarchy[1];
    LogHelper.d(TAG, "Creating playing queue for ", categoryType, ",  ", categoryValue);

        Collection<MutableMediaMetadata> tracks = null;
        // This sample only supports genre and by_search category types.
        tracks = musicProvider.getMusics();
        if (tracks == null) {
           LogHelper.e(TAG, "Unrecognized category type: ", categoryType, " for media ", mediaId);
            return null;
        }

        return convertToQueue(tracks, hierarchy[0], hierarchy[1]);
    }


    public static int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
             String mediaId) {
        int index = 0;
        for (MediaSessionCompat.QueueItem item : queue) {
            if (mediaId.equals(item.getDescription().getMediaId())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public static int getMusicIndexOnQueue(Iterable<MediaSessionCompat.QueueItem> queue,
             long queueId) {
        int index = 0;
        for (MediaSessionCompat.QueueItem item : queue) {
            if (queueId == item.getQueueId()) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private static List<MediaSessionCompat.QueueItem> convertToQueue(
            Collection<MutableMediaMetadata> tracks, String... categories) {
        List<MediaSessionCompat.QueueItem> queue = new ArrayList<>();
        int count = 0;
        for (MutableMediaMetadata track : tracks) {

            // We create a hierarchy-aware mediaID, so we know what the queue is about by looking
            // at the QueueItem media IDs.
            String hierarchyAwareMediaID = MediaIDHelper.createMediaID(
                    track.metadata.getDescription().getMediaId(), categories);

            MediaMetadataCompat trackCopy = new MediaMetadataCompat.Builder(track.metadata)
                    .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, hierarchyAwareMediaID)
                    .build();

            trackCopy.getDescription().getExtras().putInt(MediaMetadataCompat.METADATA_KEY_DURATION, track.metadata.getDescription().getExtras().getInt(MediaMetadataCompat.METADATA_KEY_DURATION));
            trackCopy.getDescription().getExtras().putInt(MutableMediaMetadata.baseCount, track.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.baseCount));
            trackCopy.getDescription().getExtras().putInt(MutableMediaMetadata.audioStatus, track.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.audioStatus));
            trackCopy.getDescription().getExtras().putLong(MutableMediaMetadata.ctime, track.metadata.getDescription().getExtras().getLong(MutableMediaMetadata.ctime));
            trackCopy.getDescription().getExtras().putInt(MutableMediaMetadata.deleted, track.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.deleted));
            trackCopy.getDescription().getExtras().putInt(MutableMediaMetadata.listenable, track.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.listenable));
            trackCopy.getDescription().getExtras().putInt(MutableMediaMetadata.size, track.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.size));
            trackCopy.getDescription().getExtras().putLong(MutableMediaMetadata.utime, track.metadata.getDescription().getExtras().getLong(MutableMediaMetadata.utime));
            trackCopy.getDescription().getExtras().putLong(MutableMediaMetadata.viewPeople,track.metadata.getDescription().getExtras().getLong(MutableMediaMetadata.viewPeople));
            trackCopy.getDescription().getExtras().putLong(MutableMediaMetadata.viewTimes, track.metadata.getDescription().getExtras().getLong(MutableMediaMetadata.viewTimes));
            trackCopy.getDescription().getExtras().putInt(MutableMediaMetadata.levelStatus, track.metadata.getDescription().getExtras().getInt(MutableMediaMetadata.levelStatus));
            trackCopy.getDescription().getExtras().putString(MutableMediaMetadata.videoId, track.metadata.getDescription().getExtras().getString(MutableMediaMetadata.videoId));

            // We don't expect queues to change after created, so we use the item index as the
            // queueId. Any other number unique in the queue would work.
            MediaSessionCompat.QueueItem item = new MediaSessionCompat.QueueItem(
                    trackCopy.getDescription(), count++);
            queue.add(item);
        }
        return queue;

    }



    /**
     * 索引是否是有效值（指向的音乐是否可以播放）
     * @param index
     * @param queue
     * @return
     */
    public static boolean isIndexPlayable(int index, List<MediaSessionCompat.QueueItem> queue) {
        return (queue != null && index >= 0 && index < queue.size());
    }

    /**
     * Determine if two queues contain identical media id's in order.
     *
     * @param list1 containing {@link MediaSessionCompat.QueueItem}'s
     * @param list2 containing {@link MediaSessionCompat.QueueItem}'s
     * @return boolean indicating whether the queue's match
     */
    public static boolean equals(List<MediaSessionCompat.QueueItem> list1,
                                 List<MediaSessionCompat.QueueItem> list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null) {
            return false;
        }
        if (list1.size() != list2.size()) {
            return false;
        }
        for (int i=0; i<list1.size(); i++) {
            if (list1.get(i).getQueueId() != list2.get(i).getQueueId()) {
                return false;
            }
            if (!TextUtils.equals(list1.get(i).getDescription().getMediaId(),
                    list2.get(i).getDescription().getMediaId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determine if queue item matches the currently playing queue item
     *
     * @param context for retrieving the {@link MediaControllerCompat}
     * @param queueItem to compare to currently playing {@link MediaSessionCompat.QueueItem}
     * @return boolean indicating whether queue item matches currently playing queue item
     */
    public static boolean isQueueItemPlaying(Activity context,
                                             MediaSessionCompat.QueueItem queueItem) {
        // Queue item is considered to be playing or paused based on both the controller's
        // current media id and the controller's active queue item id
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(context);
        if (controller != null && controller.getPlaybackState() != null) {
            long currentPlayingQueueId = controller.getPlaybackState().getActiveQueueItemId();
            String currentPlayingMediaId = controller.getMetadata().getDescription()
                    .getMediaId();
            String itemMusicId = MediaIDHelper.extractMusicIDFromMediaID(
                    queueItem.getDescription().getMediaId());
            if (queueItem.getQueueId() == currentPlayingQueueId
                    && currentPlayingMediaId != null
                    && TextUtils.equals(currentPlayingMediaId, itemMusicId)) {
                return true;
            }
        }
        return false;
    }
}
