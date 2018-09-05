package com.yushi.leke.fragment.ucenter;

import java.io.Serializable;

/**
 * 作者：Created by zhanyangyang on 2018/9/4 20:32
 * 邮箱：zhanyangyang@hzyushi.cn
 * <p>
 * 用户信息（不可编辑部分）
 */

public class MyProfileInfo implements Serializable {
    private String token;
    private String level;
    private int vote;
    private int roadShow;
    private int subscription;
    private int invitation;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getRoadShow() {
        return roadShow;
    }

    public void setRoadShow(int roadShow) {
        this.roadShow = roadShow;
    }

    public int getSubscription() {
        return subscription;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public int getInvitation() {
        return invitation;
    }

    public void setInvitation(int invitation) {
        this.invitation = invitation;
    }
}
