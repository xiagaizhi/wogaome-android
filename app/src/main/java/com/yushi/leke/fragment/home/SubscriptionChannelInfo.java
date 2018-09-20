package com.yushi.leke.fragment.home;

import java.util.List;

/**
 * 作者：Created by zhanyangyang on 2018/9/20 15:26
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class SubscriptionChannelInfo {
    private String channelId;//频道ID
    private String channelName;//频道名字
    private int more;//更多,0否,1是
    private List<Homeinfo> albumViewInfoList;


    public List<Homeinfo> getAlbumViewInfoList() {
        return albumViewInfoList;
    }

    public void setAlbumViewInfoList(List<Homeinfo> albumViewInfoList) {
        this.albumViewInfoList = albumViewInfoList;
    }

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
