package com.yushi.leke.fragment.exhibition;

/**
 * Created by mengfantao on 18/8/30.
 */

public class ExhibitionInfo {
    private String title;
    private String bgPicture;
    private int activityProgress;//活动进度（0--未开始，1--报名中，2--投票中，3--已结束）
    private String startDate;
    private String endDate;
    private String organizer;
    private String activityId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBgPicture() {
        return bgPicture;
    }

    public void setBgPicture(String bgPicture) {
        this.bgPicture = bgPicture;
    }

    public int getActivityProgress() {
        return activityProgress;
    }

    public void setActivityProgress(int activityProgress) {
        this.activityProgress = activityProgress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
