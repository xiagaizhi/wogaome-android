package com.yushi.leke.fragment.home.bean;

import com.yushi.leke.fragment.splash.advert.NativeJumpInfo;

/**
 * 作者：Created by zhanyangyang on 2018/9/19 14:23
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class BannerItemInfo {
    private String bannerId;
    private String ctime;
    private String deleted;
    private String h5Url;
    private String icon;
    private NativeJumpInfo nativeUrl;
    private String osType;
    private String sortNumber;
    private String title;
    private String utime;

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public NativeJumpInfo getNativeUrl() {
        return nativeUrl;
    }

    public void setNativeUrl(NativeJumpInfo nativeUrl) {
        this.nativeUrl = nativeUrl;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }
}
