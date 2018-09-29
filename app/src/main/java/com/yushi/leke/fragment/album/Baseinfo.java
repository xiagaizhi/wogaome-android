package com.yushi.leke.fragment.album;

public class Baseinfo {
    long albumId;
    String albumName;
    int albumStatus;
    String creator;
    String creatorInfo;
    long ctime;
    int deleted;
    String horizontalIcon;
    String introduction;
    int lockLevel;
    String shareIcon;
    String tags;
    long utime;

    public Baseinfo() {
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getAlbumStatus() {
        return albumStatus;
    }

    public void setAlbumStatus(int albumStatus) {
        this.albumStatus = albumStatus;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorInfo() {
        return creatorInfo;
    }

    public void setCreatorInfo(String creatorInfo) {
        this.creatorInfo = creatorInfo;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getHorizontalIcon() {
        return horizontalIcon;
    }

    public void setHorizontalIcon(String horizontalIcon) {
        this.horizontalIcon = horizontalIcon;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getLockLevel() {
        return lockLevel;
    }

    public void setLockLevel(int lockLevel) {
        this.lockLevel = lockLevel;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }
}
