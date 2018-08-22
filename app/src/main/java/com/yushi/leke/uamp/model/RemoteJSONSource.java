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

import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;


import com.yushi.leke.uamp.utils.LogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 从服务端获取Json格式的音乐路径列表的实用类
 */
public class RemoteJSONSource implements MusicProviderSource {

    private static final String TAG = LogHelper.makeLogTag(RemoteJSONSource.class);

    protected static final String CATALOG_URL =
        "http://storage.googleapis.com/automotive-media/music.json";

    private static final String JSON_MUSIC = "music";
    private static final String JSON_TITLE = "title";
    private static final String JSON_ALBUM = "album";
    private static final String JSON_ARTIST = "artist";
    private static final String JSON_GENRE = "genre";
    private static final String JSON_SOURCE = "source";
    private static final String JSON_IMAGE = "image";
    private static final String JSON_TRACK_NUMBER = "trackNumber";
    private static final String JSON_TOTAL_TRACK_COUNT = "totalTrackCount";
    private static final String JSON_DURATION = "duration";

    @Override
    public Iterator<MediaMetadataCompat> iterator() {
        try {
            int slashPos = CATALOG_URL.lastIndexOf('/');
            String path = CATALOG_URL.substring(0, slashPos + 1);
            JSONObject jsonObj = fetchJSONFromUrl(CATALOG_URL);//下载JSON文件
            ArrayList<MediaMetadataCompat> tracks = new ArrayList<>();
            if (jsonObj != null) {
                JSONArray jsonTracks = jsonObj.getJSONArray(JSON_MUSIC);

                if (jsonTracks != null) {
                    for (int j = 0; j < jsonTracks.length(); j++) {
                        tracks.add(buildFromJSON(jsonTracks.getJSONObject(j), path));
                    }
                }
            }
            return tracks.iterator();
        } catch (JSONException e) {
            LogHelper.e(TAG, e, "Could not retrieve music list");
            throw new RuntimeException("Could not retrieve music list", e);
        }
    }

    /**
     * 解析JSON格式的数据，构建MediaMetadata对象
     * @param json
     * @param basePath
     * @return
     * @throws JSONException
     */
    private MediaMetadataCompat buildFromJSON(JSONObject json, String basePath) throws JSONException {
        String title = json.getString(JSON_TITLE);
        String album = json.getString(JSON_ALBUM);
        String artist = json.getString(JSON_ARTIST);
        String genre = json.getString(JSON_GENRE);
        String source = json.getString(JSON_SOURCE);
        String iconUrl = json.getString(JSON_IMAGE);
        int trackNumber = json.getInt(JSON_TRACK_NUMBER);
        int totalTrackCount = json.getInt(JSON_TOTAL_TRACK_COUNT);
        int duration = json.getInt(JSON_DURATION) * 1000; // ms

        LogHelper.d(TAG, "Found music track: ", json);

        // Media is stored relative to JSON file
        if (!source.startsWith("http")) {
            //最终的地址为http://storage.googleapis.com/automotive-media/ + source
            source = basePath + source;
        }
        if (!iconUrl.startsWith("http")) {
            iconUrl = basePath + iconUrl;
        }
        // Since we don't have a unique ID in the server, we fake one using the hashcode of
        // the music source. In a real world app, this could come from the server.
        String id = String.valueOf(source.hashCode());

        // Adding the music source to the MediaMetadata (and consequently using it in the
        // mediaSession.setMetadata) is not a good idea for a real world music app, because
        // the session metadata can be accessed by notification listeners. This is done in this
        // sample for convenience only.
        //noinspection ResourceType
        // 在现实的music app中往MediaMetadata添加music source（因此在mediaSession.setMetadata中使用）并非是一个好主意
        // 因为session metadata可以被notification监听者访问
        // 为了方便起见，请按本示例进行操作
        return new MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, id)
                .putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, source)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, artist)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, iconUrl)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, title)
                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, trackNumber)
                .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, totalTrackCount)
                .build();
    }

    /**
     * 从服务端下载JSON文件，解析并返回JSON object
     *
     * @return result JSONObject containing the parsed representation.
     */
    private JSONObject fetchJSONFromUrl(String urlString) throws JSONException {
        String data="{\"music\" : [ \n" +
                "\t{ \"title\" : \"Jazz in Paris\",\n" +
                "\t  \"album\" : \"Jazz & Blues\",\n" +
                "\t  \"artist\" : \"Media Right Productions\",\n" +
                "\t  \"genre\" : \"Jazz & Blues\",\n" +
                "\t  \"source\" : \"Jazz_In_Paris.mp3\",\n" +
                "\t  \"image\" : \"album_art.jpg\",\n" +
                "\t  \"trackNumber\" : 1,\n" +
                "\t  \"totalTrackCount\" : 6,\n" +
                "\t  \"duration\" : 103,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"The Messenger\",\n" +
                "\t  \"album\" : \"Jazz & Blues\",\n" +
                "\t  \"artist\" : \"Silent Partner\",\n" +
                "\t  \"genre\" : \"Jazz & Blues\",\n" +
                "\t  \"source\" : \"The_Messenger.mp3\",\n" +
                "\t  \"image\" : \"album_art.jpg\",\n" +
                "\t  \"trackNumber\" : 2,\n" +
                "\t  \"totalTrackCount\" : 6,\n" +
                "\t  \"duration\" : 132,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Talkies\",\n" +
                "\t  \"album\" : \"Jazz & Blues\",\n" +
                "\t  \"artist\" : \"Huma-Huma\",\n" +
                "\t  \"genre\" : \"Jazz & Blues\",\n" +
                "\t  \"source\" : \"Talkies.mp3\",\n" +
                "\t  \"image\" : \"album_art.jpg\",\n" +
                "\t  \"trackNumber\" : 3,\n" +
                "\t  \"totalTrackCount\" : 6,\n" +
                "\t  \"duration\" : 162,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"On the Bach\",\n" +
                "\t  \"album\" : \"Cinematic\",\n" +
                "\t  \"artist\" : \"Jingle Punks\",\n" +
                "\t  \"genre\" : \"Cinematic\",\n" +
                "\t  \"source\" : \"On_the_Bach.mp3\",\n" +
                "\t  \"image\" : \"album_art.jpg\",\n" +
                "\t  \"trackNumber\" : 4,\n" +
                "\t  \"totalTrackCount\" : 6,\n" +
                "\t  \"duration\" : 66,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"The Story Unfolds\",\n" +
                "\t  \"album\" : \"Cinematic\",\n" +
                "\t  \"artist\" : \"Jingle Punks\",\n" +
                "\t  \"genre\" : \"Cinematic\",\n" +
                "\t  \"source\" : \"The_Story_Unfolds.mp3\",\n" +
                "\t  \"image\" : \"album_art.jpg\",\n" +
                "\t  \"trackNumber\" : 5,\n" +
                "\t  \"totalTrackCount\" : 6,\n" +
                "\t  \"duration\" : 91,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Drop and Roll\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock\",\n" +
                "\t  \"artist\" : \"Silent Partner\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Drop_and_Roll.mp3\",\n" +
                "\t  \"image\" : \"album_art_2.jpg\",\n" +
                "\t  \"trackNumber\" : 1,\n" +
                "\t  \"totalTrackCount\" : 7,\n" +
                "\t  \"duration\" : 121,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Motocross\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock\",\n" +
                "\t  \"artist\" : \"Topher Mohr and Alex Elena\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Motocross.mp3\",\n" +
                "\t  \"image\" : \"album_art_2.jpg\",\n" +
                "\t  \"trackNumber\" : 2,\n" +
                "\t  \"totalTrackCount\" : 7,\n" +
                "\t  \"duration\" : 182,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Wish You'd Come True\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock\",\n" +
                "\t  \"artist\" : \"The 126ers\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Wish_You_d_Come_True.mp3\",\n" +
                "\t  \"image\" : \"album_art_2.jpg\",\n" +
                "\t  \"trackNumber\" : 3,\n" +
                "\t  \"totalTrackCount\" : 7,\n" +
                "\t  \"duration\" : 169,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Awakening\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock\",\n" +
                "\t  \"artist\" : \"Silent Partner\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Awakening.mp3\",\n" +
                "\t  \"image\" : \"album_art_2.jpg\",\n" +
                "\t  \"trackNumber\" : 4,\n" +
                "\t  \"totalTrackCount\" : 7,\n" +
                "\t  \"duration\" : 220,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Home\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock\",\n" +
                "\t  \"artist\" : \"Letter Box\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Home.mp3\",\n" +
                "\t  \"image\" : \"album_art_2.jpg\",\n" +
                "\t  \"trackNumber\" : 5,\n" +
                "\t  \"totalTrackCount\" : 7,\n" +
                "\t  \"duration\" : 213,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Tell The Angels\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock\",\n" +
                "\t  \"artist\" : \"Letter Box\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Tell_The_Angels.mp3\",\n" +
                "\t  \"image\" : \"album_art_2.jpg\",\n" +
                "\t  \"trackNumber\" : 6,\n" +
                "\t  \"totalTrackCount\" : 7,\n" +
                "\t  \"duration\" : 208,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Hey Sailor\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock\",\n" +
                "\t  \"artist\" : \"Letter Box\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Hey_Sailor.mp3\",\n" +
                "\t  \"image\" : \"album_art_2.jpg\",\n" +
                "\t  \"trackNumber\" : 7,\n" +
                "\t  \"totalTrackCount\" : 7,\n" +
                "\t  \"duration\" : 193,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"Keys To The Kingdom\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock 2\",\n" +
                "\t  \"artist\" : \"The 126ers\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"Keys_To_The_Kingdom.mp3\",\n" +
                "\t  \"image\" : \"album_art_3.jpg\",\n" +
                "\t  \"trackNumber\" : 1,\n" +
                "\t  \"totalTrackCount\" : 2,\n" +
                "\t  \"duration\" : 221,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t},\n" +
                "\t{ \"title\" : \"The Coldest Shoulder\",\n" +
                "\t  \"album\" : \"Youtube Audio Library Rock 2\",\n" +
                "\t  \"artist\" : \"The 126ers\",\n" +
                "\t  \"genre\" : \"Rock\",\n" +
                "\t  \"source\" : \"The_Coldest_Shoulder.mp3\",\n" +
                "\t  \"image\" : \"album_art_3.jpg\",\n" +
                "\t  \"trackNumber\" : 2,\n" +
                "\t  \"totalTrackCount\" : 2,\n" +
                "\t  \"duration\" : 160,\n" +
                "\t  \"site\" : \"https://www.youtube.com/audiolibrary/music\"\n" +
                "\t}\n" +
                "]}";

        try {
            return new JSONObject(data);
        } catch (JSONException e) {
            throw e;
        } catch (Exception e) {
            LogHelper.e(TAG, "Failed to parse the json for media list", e);
            return null;
        } finally {

        }
    }
}
