package com.yushi.leke.share;

import com.yufan.library.R;

/**
 * Created by mengfantao on 17/8/29.
 *
 * 底部交互菜单，例：分享
 *
 */

public enum MenuType {
    WEIXIN(1, R.drawable.share_weixin, "微信"),
    QQ(2, R.drawable.share_qq, "QQ"),
    WEIXIN_MOMENTS(3, R.drawable.share_pengyouquan, "朋友圈"),
    WEIBO(4, R.drawable.share_weibo, "微博"),
    QQ_SPACE(5, R.drawable.share_qqspace, "QQ空间");
    private int type;
    private int icon;
    private String name;

    MenuType(int type, int icon, String name) {
        this.type = type;
        this.icon = icon;
        this.name = name;
    }

    public static MenuType getMenuTypeByType(int val) {
        for (MenuType p : values()) {
            if (p.getType() == val) return p;
        }
        return null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
