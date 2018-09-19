package com.yushi.leke.fragment.splash.advert;

import java.io.Serializable;

/**
 * 作者：Created by zhanyangyang on 2018/9/19 18:00
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class NativeJumpInfo implements Serializable {

    /**
     * 0：活动 1：专辑
     */
    private String detailType;

    /**
     * 活动id或者专辑id
     */
    private String detailId;

    /**
     * 活动进度（0--未开始，1--报名中，2--投票中，3--已结束）
     */
    private int activityProgress;


    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public int getActivityProgress() {
        return activityProgress;
    }

    public void setActivityProgress(int activityProgress) {
        this.activityProgress = activityProgress;
    }
}
