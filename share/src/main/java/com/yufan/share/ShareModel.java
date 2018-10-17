package com.yufan.share;

import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;

import java.io.Serializable;


public class ShareModel implements Serializable {
    private String title;
    private String content;

    private String icon;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    private String targetUrl;
    private UMEmoji emojiMedia;
    private UMImage imageMedia;
    private UMusic musicMedia;
    private UMVideo videoMedia;
    private UMWeb mUMWeb;

    public UMWeb getmUMWeb() {
        return mUMWeb;
    }

    public void setmUMWeb(UMWeb mUMWeb) {
        this.mUMWeb = mUMWeb;
    }

    private String shareSource="资讯";//1:分享公会，2:分享资讯，6:分享话题，其他：分享直播

    public String getShareSource() {
        return shareSource;
    }

    public void setShareSource(String shareSource) {
        this.shareSource = shareSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UMEmoji getEmojiMedia() {
        return emojiMedia;
    }

    public void setEmojiMedia(UMEmoji emojiMedia) {
        this.emojiMedia = emojiMedia;
    }

    public UMImage getImageMedia() {

        return imageMedia;
    }

    public void setImageMedia(UMImage imageMedia) {
        this.imageMedia = imageMedia;
    }

    public UMusic getMusicMedia() {
        return musicMedia;
    }

    public void setMusicMedia(UMusic musicMedia) {
        this.musicMedia = musicMedia;
    }

    public UMVideo getVideoMedia() {
        return videoMedia;
    }

    public void setVideoMedia(UMVideo videoMedia) {
        this.videoMedia = videoMedia;
    }

    /**
     * 是否需要统计分享
     */
    private boolean isNeedCount;

    public boolean isNeedCount() {
        return isNeedCount;
    }

    public void setNeedCount(boolean needCount) {
        isNeedCount = needCount;
    }
}
