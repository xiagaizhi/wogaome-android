package com.yufan.share;


import com.umeng.socialize.bean.SHARE_MEDIA;

public interface IShareCallback {
    void onStart(SHARE_MEDIA share_media);
    void onSuccess();
    void onFaild();
    void onCancel();
}
