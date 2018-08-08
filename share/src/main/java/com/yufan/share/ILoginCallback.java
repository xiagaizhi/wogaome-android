package com.yufan.share;

import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


public interface ILoginCallback {
    void onSuccess(Map<String, String> stringStringMap, SHARE_MEDIA platform, Map<String, String> data);
    void onFaild(String msg);
    void onCancel();
}
