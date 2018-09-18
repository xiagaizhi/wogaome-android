package com.yushi.leke.fragment.searcher;

/**
 * Created by mengfantao on 18/8/31.
 */

public class SearchActionInfo {
    String qq;//报名联系QQ
    long utime;
    String activityTagIds;//活动标签，多个Id用逗号分隔
    String creatorId;//创建活动的管理员id
    String mobile;//报名联系电话
    long voteStartTime;//活动投票开始时间
    String title;//活动标题
    String horizontalIcon;//横屏大图
    long enrollStartTime;//活动报名开始时间
    long activityEndTime;//活动总周期结束时间
    String subTitle;//分享副标题
    String organizer;//主办方
    String shareIcon;//分享icon
    long ctime;
    int ranking;//获胜名次
    long id;
    String verticalIcon;//竖屏大图
    String introduction;
    int status;//活动状态：0-上架中, 1-已下架

    public SearchActionInfo() {

    }


    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public long getUtime() {
        return utime;
    }

    public void setUtime(long utime) {
        this.utime = utime;
    }

    public String getActivityTagIds() {
        return activityTagIds;
    }

    public void setActivityTagIds(String activityTagIds) {
        this.activityTagIds = activityTagIds;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getVoteStartTime() {
        return voteStartTime;
    }

    public void setVoteStartTime(long voteStartTime) {
        this.voteStartTime = voteStartTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHorizontalIcon() {
        return horizontalIcon;
    }

    public void setHorizontalIcon(String horizontalIcon) {
        this.horizontalIcon = horizontalIcon;
    }

    public long getEnrollStartTime() {
        return enrollStartTime;
    }

    public void setEnrollStartTime(long enrollStartTime) {
        this.enrollStartTime = enrollStartTime;
    }

    public long getActivityEndTime() {
        return activityEndTime;
    }

    public void setActivityEndTime(long activityEndTime) {
        this.activityEndTime = activityEndTime;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVerticalIcon() {
        return verticalIcon;
    }

    public void setVerticalIcon(String verticalIcon) {
        this.verticalIcon = verticalIcon;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
