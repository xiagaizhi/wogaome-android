package com.yushi.leke.fragment.album.audioList;

/**
 * Created by mengfantao on 18/9/19.
 */

public class AlbumAudio {
    int albumId;

    String aliVideoId;// 此音频在阿里云的Id


    int audioId;

    String audioName;

    int audioStatus;//0下架,1上架


    int baseCount;//基础播放数


    int ctime;

    int deleted;

    int duration;// 时长


    int listenable;//是否试听0否,1是


    int size;// 大小


    long utime;

    int viewPeople;//播放人数


    int viewTimes;//播放次数

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

    public int getAudioId() {
        return audioId;
    }

    public void setAudioId(int audioId) {
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

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
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

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public int getViewPeople() {
        return viewPeople;
    }

    public void setViewPeople(int viewPeople) {
        this.viewPeople = viewPeople;
    }

    public int getViewTimes() {
        return viewTimes;
    }

    public void setViewTimes(int viewTimes) {
        this.viewTimes = viewTimes;
    }
}
