package com.yushi.leke.fragment.home;

/**
 * Created by mengfantao on 18/8/30.
 */

public class AudioInfo {
    String creator;//创建者
    long utime;
    String audioId;
    String albumId;
    String tags;//专辑标签，多个用逗号分隔
    int baseCount;//基础播放数
    int duration;//音频时长 单位：秒
    int deleted;
    int size;//音频大小
    String audioName;//音频标题
    long ctime;
    String aliVideoId;//此音频在阿里云的ID
    int viewPeople;//播放人数
    int viewTimes;//播放次数
    int audioStatus;//0下架,1上架
    int listenable;//是否试听
    String icon;//横屏大图
    public AudioInfo() {
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getBaseCount() {
        return baseCount;
    }

    public void setBaseCount(int baseCount) {
        this.baseCount = baseCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getAliVideoId() {
        return aliVideoId;
    }

    public void setAliVideoId(String aliVideoId) {
        this.aliVideoId = aliVideoId;
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

    public int getAudioStatus() {
        return audioStatus;
    }

    public void setAudioStatus(int audioStatus) {
        this.audioStatus = audioStatus;
    }

    public int getListenable() {
        return listenable;
    }

    public void setListenable(int listenable) {
        this.listenable = listenable;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
