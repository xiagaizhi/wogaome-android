package com.yushi.leke.fragment.browser;

import java.util.List;

/**
 * 作者：Created by zhanyangyang on 2018/9/28 11:05
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class NaviBarInfoList {
    private List<NaviBarInfo> actions;
    private String title;

    public List<NaviBarInfo> getActions() {
        return actions;
    }

    public void setActions(List<NaviBarInfo> actions) {
        this.actions = actions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
