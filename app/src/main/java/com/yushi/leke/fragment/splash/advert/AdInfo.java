package com.yushi.leke.fragment.splash.advert;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 作者：Created by zhanyangyang on 2018/9/15 09:49
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class AdInfo implements Serializable{
    private String adKey;
    private String adImgurl;
    private String actionUrl;

    private byte[] bitmap;

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getAdKey() {
        return adKey;
    }

    public void setAdKey(String adKey) {
        this.adKey = adKey;
    }

    public String getAdImgurl() {
        return adImgurl;
    }

    public void setAdImgurl(String adImgurl) {
        this.adImgurl = adImgurl;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }
}
