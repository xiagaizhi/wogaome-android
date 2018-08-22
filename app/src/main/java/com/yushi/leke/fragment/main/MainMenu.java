package com.yushi.leke.fragment.main;


import com.yushi.leke.R;

/**
 * Created by mengfantao on 17/12/25.
 * 首页枚举
 */

public enum MainMenu {
    SUBSCRIBE_COLUM("订阅专栏",R.drawable.tab_icon_subscribe),
    ROAD_SHOW_HALL("路演大厅", R.drawable.tab_icon_roadshow),
    MY("我的", R.drawable.tab_icon_my);
    private String title;
    private Class<?> clz;
    private int icon;
    MainMenu(String title,int icon) {
        this.title = title;
        this.icon = icon;

    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


}
