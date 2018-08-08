package com.yufan.share;

import android.app.Activity;
import android.content.Intent;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


public class ShareUtils {
    private UMShareAPI mShareAPI = null;
    private Activity mActivity;
    private ILoginCallback loginCallback;
    private IShareCallback shareCallback;

    public ShareUtils(Activity activity) {
        this.mActivity = activity;
        if (mShareAPI == null) {
            mShareAPI = UMShareAPI.get(activity);//获取UMShareAPI
        }

    }

    public void login(SHARE_MEDIA platform, ILoginCallback callback) {
        this.loginCallback = callback;
        mShareAPI.doOauthVerify(mActivity, platform, umAuthListener);//进行平台授权 umAuthListener授权是否成功的回调
    }
    public void share(ShareModel model, IShareCallback shareCallback) {
        this.shareCallback = shareCallback;
        //初始化分享面板
        new ShareAction(mActivity).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)

                .withText(model.getContent())
                .withMedia(model.getImageMedia())
                .setCallback(umShareListener)
                .open();
    }

    public boolean isInstall(Activity activity, SHARE_MEDIA share_media) {
        return mShareAPI.isInstall(activity, share_media);
    }

    /**
     * 单平台分享(图片Media)
     */
    public void sIMShare(SHARE_MEDIA platform, Activity activity, ShareModel model, IShareCallback shareCallback ) {
        this.shareCallback = shareCallback;
        new ShareAction(activity)
                .setPlatform(platform)
                .setCallback(umShareListener)
                .withText(model.getContent())
                .withMedia(model.getImageMedia())

                .share();
    }

    /**
     * 单平台分享(图片Media)
     */
    public void sWebShare(SHARE_MEDIA platform, Activity activity, ShareModel model, IShareCallback shareCallback ) {
        this.shareCallback = shareCallback;
        new ShareAction(activity)
                .setPlatform(platform)
                .setCallback(umShareListener)
                .withText(model.getContent())
                .withMedia(model.getmUMWeb())
                .share();
    }
    /**
     * 单平台分享(表情Media)
     */
    public void sEMShare(SHARE_MEDIA platform, Activity activity, ShareModel model, IShareCallback shareCallback ) {
        this.shareCallback = shareCallback;
        new ShareAction(activity)
                .setPlatform(platform)
                .setCallback(umShareListener)
                .withText(model.getContent())

                .withMedia(model.getEmojiMedia())

                .share();
    }

    /**
     * 单平台分享(音乐Media)
     */
    public void sMMShare(SHARE_MEDIA platform, Activity activity, ShareModel model, IShareCallback shareCallback ) {
        this.shareCallback = shareCallback;
        new ShareAction(activity)
                .setPlatform(platform)
                .setCallback(umShareListener)
                .withText(model.getContent())

                .withMedia(model.getMusicMedia())

                .share();
    }

    /**
     * 单平台分享(视频Media)
     */
    public void sVMShare(SHARE_MEDIA platform, Activity activity, ShareModel model, IShareCallback shareCallback ) {
        this.shareCallback = shareCallback;
        new ShareAction(activity)
                .setPlatform(platform)
                .setCallback(umShareListener)
                .withText(model.getContent())

                .withMedia(model.getVideoMedia())

                .share();
    }

    /**********
     * 内部方法
     ***********/
    private Map<String, String> oAuthData;
    private UMAuthListener umAuthListener = new UMAuthListener() {


        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> oAuthData) {
            ShareUtils.this.oAuthData = oAuthData;
            if (oAuthData != null) {
                mShareAPI.getPlatformInfo(mActivity, platform, getInfoListener);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            loginCallback.onFaild("授权失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            loginCallback.onCancel();
        }
    };
    private UMAuthListener getInfoListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null) {
                loginCallback.onSuccess(data,platform,oAuthData);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            loginCallback.onFaild("获取用户信息失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            loginCallback.onCancel();
        }
    };
    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            shareCallback.onStart(share_media);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            shareCallback.onSuccess();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            shareCallback.onFaild();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            shareCallback.onCancel();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
