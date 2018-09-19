package com.yushi.leke.fragment.splash.advert;


import java.io.Serializable;

/**
 * 作者：Created by zhanyangyang on 2018/9/15 09:49
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class AdInfo implements Serializable{
    private String id;
    private String icon;
    private String h5Url;
    private String nativeUrl;


    private byte[] bitmap;

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getNativeUrl() {
        return nativeUrl;
    }

    public void setNativeUrl(String nativeUrl) {
        this.nativeUrl = nativeUrl;
    }
}
