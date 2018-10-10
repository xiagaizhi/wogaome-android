package com.yushi.leke.fragment.album;

public class AlbumDetailinfo {
    Baseinfo albumDetailInfo;
    int albumViewTimes;
    int audioQuantity;
    String h5Url;
    public AlbumDetailinfo() {
    }

    public Baseinfo getAlbumDetailInfo() {
        return albumDetailInfo;
    }

    public void setAlbumDetailInfo(Baseinfo albumDetailInfo) {
        this.albumDetailInfo = albumDetailInfo;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public int getAlbumViewTimes() {
        return albumViewTimes;
    }

    public void setAlbumViewTimes(int albumViewTimes) {
        this.albumViewTimes = albumViewTimes;
    }

    public int getAudioQuantity() {
        return audioQuantity;
    }

    public void setAudioQuantity(int audioQuantity) {
        this.audioQuantity = audioQuantity;
    }
}
