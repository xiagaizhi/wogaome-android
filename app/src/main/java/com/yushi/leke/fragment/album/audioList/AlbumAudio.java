package com.yushi.leke.fragment.album.audioList;

/**
 * Created by mengfantao on 18/9/19.
 */

public class AlbumAudio {
    int albumId;

    String aliVideoId;// 此音频在阿里云的Id


    String audioId;

    String audioName;

    int audioStatus;//0下架,1上架


    int baseCount;//基础播放数


    long ctime;

    int deleted;

    int duration;// 时长


    int listenable;//是否试听0否,1是


    int size;// 大小



    long viewPeople;//播放人数


    long viewTimes;//播放次数

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAliVideoId() {
        return aliVideoId;
    }

    public void setAliVideoId(String aliVideoId) {
        this.aliVideoId = aliVideoId;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public int getAudioStatus() {
        return audioStatus;
    }

    public void setAudioStatus(int audioStatus) {
        this.audioStatus = audioStatus;
    }

    public int getBaseCount() {
        return baseCount;
    }

    public void setBaseCount(int baseCount) {
        this.baseCount = baseCount;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getListenable() {
        return listenable;
    }

    public void setListenable(int listenable) {
        this.listenable = listenable;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public long getViewPeople() {
        return viewPeople;
    }

    public void setViewPeople(long viewPeople) {
        this.viewPeople = viewPeople;
    }

    public long getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(long viewTimes) {
        this.viewTimes = viewTimes;
    }
}
