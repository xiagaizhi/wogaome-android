package com.yushi.leke.fragment.home.bean;

/**
 * 作者：Created by zhanyangyang on 2018/9/20 14:50
 * 邮箱：zhanyangyang@hzyushi.cn
 *
 * 栏目标签
 */

public class SubscriptionColumnInfo {
    private String channelId;//频道ID
    private String channelName;//频道名字
    private int more;//更多,0否,1是

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
