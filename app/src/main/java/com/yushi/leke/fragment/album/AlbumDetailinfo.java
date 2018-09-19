package com.yushi.leke.fragment.album;

public class AlbumDetailinfo {
    Baseinfo album;
    int albumViewTimes;
    int audioQuantity;
    public AlbumDetailinfo() {
    }

    public Baseinfo getAlbum() {
        return album;
    }

    public void setAlbum(Baseinfo album) {
        this.album = album;
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
