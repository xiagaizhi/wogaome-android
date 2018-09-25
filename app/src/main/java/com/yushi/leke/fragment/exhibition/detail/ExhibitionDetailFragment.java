package com.yushi.leke.fragment.exhibition.detail;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.constants.PlayParameter;
import com.aliyun.vodplayerview.utils.ScreenUtils;
import com.aliyun.vodplayerview.utils.VidStsUtil;
import com.aliyun.vodplayerview.view.control.ControlView;
import com.aliyun.vodplayerview.view.tipsview.ErrorInfo;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.DialogManager;
import com.yufan.share.ShareModel;
import com.yushi.leke.R;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.browser.BrowserBaseFragment;
import com.yushi.leke.fragment.exhibition.voteend.VoteendFragment;
import com.yushi.leke.fragment.exhibition.voteing.VoteingFragment;
import com.yushi.leke.share.ShareMenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(ExhibitionDetailVu.class)
public class ExhibitionDetailFragment extends BaseFragment<ExhibitionDetailContract.IView> implements ExhibitionDetailContract.Presenter, ICallBack {
    private AliyunVodPlayerView mAliyunVodPlayerView = null;
    private ErrorInfo currentError = ErrorInfo.Normal;
    /**
     * get StsToken stats
     */
    private boolean inRequest;
    private int exhibitionType;
    private String activityid;
    private String currentVid;
    private String currentTitle;
    private String currentProjectId;
    private boolean isSuccessRequestAliplayerInfo;
    private ShareInfo mShareInfo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAliyunPlayerView();
        switch (exhibitionType) {//活动进度（0--未开始，1--报名中，2--投票中，3--已结束）
            case 0:
                loadRootFragment(R.id.fl_exhibition_content, UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "").build());
                break;
            case 1:
                loadRootFragment(R.id.fl_exhibition_content, UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "").build());
                break;
            case 2:
                VoteingFragment mVoteingFragment = (VoteingFragment) UIHelper.creat(VoteingFragment.class)
                        .put(Global.BUNDLE_KEY_ACTIVITYID, activityid)
                        .build();
                mVoteingFragment.setmICallBack(this);
                loadRootFragment(R.id.fl_exhibition_content, mVoteingFragment);
                break;
            case 3:
                VoteendFragment voteendFragment = (VoteendFragment) UIHelper.creat(VoteendFragment.class)
                        .put(Global.BUNDLE_KEY_ACTIVITYID, activityid)
                        .build();
                voteendFragment.setmICallBack(this);
                loadRootFragment(R.id.fl_exhibition_content, voteendFragment);
                break;
        }
        getShareinfo(false);
    }

    @Override
    public void getBundleDate() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            exhibitionType = bundle.getInt(Global.BUNDLE_KEY_EXHIBITION_TYE);
            activityid = bundle.getString(Global.BUNDLE_KEY_ACTIVITYID);
        }
    }

    private void getShareinfo(final boolean isAutoStart) {
        if (isAutoStart) {
            DialogManager.getInstance().showLoadingDialog();
        }
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).sharedetail(activityid))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            mShareInfo = JSON.parseObject(mApiBean.getData(), ShareInfo.class);
                            if (isAutoStart) {
                                ShareModel shareModel = new ShareModel();
                                shareModel.setContent(mShareInfo.getSubTitle());
                                shareModel.setTitle(mShareInfo.getTitle());
                                shareModel.setIcon(mShareInfo.getShareIcon());
                                shareModel.setTargetUrl(ApiManager.getInstance().getApiConfig().getExhibitionDetail(activityid));
                                ShareMenuActivity.startShare(ExhibitionDetailFragment.this,shareModel);
                            }
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {
                        if (isAutoStart) {
                            DialogManager.getInstance().dismiss();
                        }
                    }
                });
    }

    /**
     * 回传视频vid
     *
     * @param s
     */
    @Override
    public void OnBackResult(Object... s) {
        String tempVid = (String) s[0];
        String tempTitle = (String) s[1];
        String tempProjectId = (String) s[2];
        if (!TextUtils.equals(currentVid, tempVid)) {//当前播放vid和需要切换vid不同才做切换操作
            currentVid = tempVid;
            currentTitle = tempTitle;
            currentProjectId = tempProjectId;
            getAliplayerInfo();
        }
    }

    /**
     * 获取阿里云播放所需配置
     */
    private void getAliplayerInfo() {
        isSuccessRequestAliplayerInfo = false;
//        DialogManager.getInstance().showLoadingDialog();
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).playVideoForProject()).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    try {
                        JSONObject jsonObject = new JSONObject(mApiBean.getData());
                        PlayParameter.PLAY_PARAM_SCU_TOKEN = jsonObject.getString("token");
                        PlayParameter.PLAY_PARAM_AK_ID = jsonObject.getString("keyId");
                        PlayParameter.PLAY_PARAM_AK_SECRE = jsonObject.getString("keySecret");
                        isSuccessRequestAliplayerInfo = true;
                        changePlayVidSource(currentVid, currentTitle);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {
                if (!isSuccessRequestAliplayerInfo) {
                    currentVid = "";
                    currentTitle = "";
                    currentProjectId = "";
                }
//                DialogManager.getInstance().dismiss();
            }
        });
    }


    @Override
    public void onRefresh() {

    }


    /**
     * 播放本地资源
     *
     * @param url
     * @param title
     */
    private void changePlayLocalSource(String url, String title) {
        AliyunLocalSource.AliyunLocalSourceBuilder alsb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        alsb.setSource(url);
        alsb.setTitle(title);
        AliyunLocalSource localSource = alsb.build();
        mAliyunVodPlayerView.setLocalSource(localSource);
    }

    /**
     * 切换播放vid资源
     *
     * @param vid   切换视频的vid
     * @param title 切换视频的title
     */
    private void changePlayVidSource(String vid, String title) {
        AliyunVidSts vidSts = new AliyunVidSts();
        vidSts.setVid(vid);
        vidSts.setAcId(PlayParameter.PLAY_PARAM_AK_ID);
        vidSts.setAkSceret(PlayParameter.PLAY_PARAM_AK_SECRE);
        vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
        vidSts.setTitle(title);
        mAliyunVodPlayerView.setVidSts(vidSts);
        playVideoCount();
    }

    /**
     * 上报 视频播放数
     */
    private void playVideoCount() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).playVideoCount(currentProjectId)).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {

            }

            @Override
            public void onResponse(ApiBean mApiBean) {

            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void initAliyunPlayerView() {
        mAliyunVodPlayerView = getVu().getAliyunVodPlayerView();
        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true);
//        PlayParameter.PLAY_PARAM_URL = DEFAULT_URL;
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        mAliyunVodPlayerView.setPlayingCache(false, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        mAliyunVodPlayerView.setAutoPlay(true);
        mAliyunVodPlayerView.setOnPreparedListener(new MyPrepareListener(this));
        mAliyunVodPlayerView.setNetConnectedListener(new MyNetConnectedListener(this));
        mAliyunVodPlayerView.setOnCompletionListener(new MyCompletionListener(this));
        mAliyunVodPlayerView.setOnFirstFrameStartListener(new MyFrameInfoListener(this));
        mAliyunVodPlayerView.setOnChangeQualityListener(new MyChangeQualityListener(this));
        mAliyunVodPlayerView.setOnStoppedListener(new MyStoppedListener(this));
        mAliyunVodPlayerView.setOrientationChangeListener(new MyOrientationChangeListener(this));
        mAliyunVodPlayerView.setOnUrlTimeExpiredListener(new MyOnUrlTimeExpiredListener(this));
        mAliyunVodPlayerView.setOnShowMoreClickListener(new MyShowMoreClickLisener(this));

        mAliyunVodPlayerView.enableNativeLog();
        mAliyunVodPlayerView.setmOnPlayerViewClickListener(new AliyunVodPlayerView.OnPlayerViewClickListener() {
            @Override
            public void onClick(AliyunScreenMode screenMode, AliyunVodPlayerView.PlayViewType viewType) {
                if (viewType == AliyunVodPlayerView.PlayViewType.BackPressed) {
                    pop();
                }
            }
        });
    }


    private boolean isStrangePhone() {
        boolean strangePhone = "mx5".equalsIgnoreCase(Build.DEVICE)
                || "Redmi Note2".equalsIgnoreCase(Build.DEVICE)
                || "Z00A_1".equalsIgnoreCase(Build.DEVICE)
                || "hwH60-L02".equalsIgnoreCase(Build.DEVICE)
                || "hermes".equalsIgnoreCase(Build.DEVICE)
                || ("V4".equalsIgnoreCase(Build.DEVICE) && "Meitu".equalsIgnoreCase(Build.MANUFACTURER))
                || ("m1metal".equalsIgnoreCase(Build.DEVICE) && "Meizu".equalsIgnoreCase(Build.MANUFACTURER));

        VcPlayerLog.e("lfj1115 ", " Build.Device = " + Build.DEVICE + " , isStrange = " + strangePhone);
        return strangePhone;
    }

    private static class MyPrepareListener implements IAliyunVodPlayer.OnPreparedListener {

        private WeakReference<ExhibitionDetailFragment> weakReference;

        public MyPrepareListener(ExhibitionDetailFragment skinFragment) {
            weakReference = new WeakReference<ExhibitionDetailFragment>(skinFragment);
        }

        @Override
        public void onPrepared() {

        }
    }


    private static class MyCompletionListener implements IAliyunVodPlayer.OnCompletionListener {

        private WeakReference<ExhibitionDetailFragment> weakReference;

        public MyCompletionListener(ExhibitionDetailFragment skinFragment) {
            weakReference = new WeakReference<ExhibitionDetailFragment>(skinFragment);
        }

        @Override
        public void onCompletion() {

            ExhibitionDetailFragment activity = weakReference.get();
            if (activity != null) {
                activity.onCompletion();
            }
        }
    }

    private void onCompletion() {
        // 当前视频播放结束, 播放下一个视频
        onNext();
    }

    private void onNext() {
        if (currentError == ErrorInfo.UnConnectInternet) {
            // 此处需要判断网络和播放类型
            // 网络资源, 播放完自动波下一个, 无网状态提示ErrorTipsView
            // 本地资源, 播放完需要重播, 显示Replay, 此处不需要处理
            if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
                mAliyunVodPlayerView.showErrorTipView(4014, -1, "当前网络不可用");
            }
            return;
        }
//下一个

    }

    @Override
    public boolean onBackPressedSupport() {
        if (mAliyunVodPlayerView != null) {
            boolean handler = mAliyunVodPlayerView.onKeyDown(KeyEvent.KEYCODE_BACK, null);
            if (handler) {
                return true;
            }
        }
        return super.onBackPressedSupport();
    }

    private static class MyFrameInfoListener implements IAliyunVodPlayer.OnFirstFrameStartListener {

        private WeakReference<ExhibitionDetailFragment> weakReference;

        public MyFrameInfoListener(ExhibitionDetailFragment skinFragment) {
            weakReference = new WeakReference<ExhibitionDetailFragment>(skinFragment);
        }

        @Override
        public void onFirstFrameStart() {

        }
    }


    private static class MyChangeQualityListener implements IAliyunVodPlayer.OnChangeQualityListener {

        private WeakReference<ExhibitionDetailFragment> weakReference;

        public MyChangeQualityListener(ExhibitionDetailFragment skinFragment) {
            weakReference = new WeakReference<ExhibitionDetailFragment>(skinFragment);
        }

        @Override
        public void onChangeQualitySuccess(String finalQuality) {

            ExhibitionDetailFragment fragment = weakReference.get();
            if (fragment != null) {
                fragment.onChangeQualitySuccess(finalQuality);
            }
        }

        @Override
        public void onChangeQualityFail(int code, String msg) {
            ExhibitionDetailFragment fragment = weakReference.get();
            if (fragment != null) {
                fragment.onChangeQualityFail(code, msg);
            }
        }
    }

    private void onChangeQualitySuccess(String finalQuality) {
        Toast.makeText(getContext(),
                getString(com.aliyun.vodplayer.R.string.log_change_quality_success), Toast.LENGTH_SHORT).show();
    }

    void onChangeQualityFail(int code, String msg) {
        Toast.makeText(getContext(),
                getString(com.aliyun.vodplayer.R.string.log_change_quality_fail), Toast.LENGTH_SHORT).show();
    }

    private static class MyStoppedListener implements IAliyunVodPlayer.OnStoppedListener {

        private WeakReference<ExhibitionDetailFragment> activityWeakReference;

        public MyStoppedListener(ExhibitionDetailFragment skinActivity) {
            activityWeakReference = new WeakReference<ExhibitionDetailFragment>(skinActivity);
        }

        @Override
        public void onStopped() {

            ExhibitionDetailFragment activity = activityWeakReference.get();
            if (activity != null) {
                activity.onStopped();
            }
        }
    }


    private void onStopped() {
        Toast.makeText(getContext(), com.aliyun.vodplayer.R.string.log_play_stopped,
                Toast.LENGTH_SHORT).show();
    }

    private void setPlaySource() {
        if ("localSource".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            AliyunLocalSource.AliyunLocalSourceBuilder alsb = new AliyunLocalSource.AliyunLocalSourceBuilder();
            alsb.setSource(PlayParameter.PLAY_PARAM_URL);
            Uri uri = Uri.parse(PlayParameter.PLAY_PARAM_URL);
            if ("rtmp".equals(uri.getScheme())) {
                alsb.setTitle("");
            }
            AliyunLocalSource localSource = alsb.build();
            mAliyunVodPlayerView.setLocalSource(localSource);
        } else if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            if (!inRequest) {
                AliyunVidSts vidSts = new AliyunVidSts();
                vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                vidSts.setAcId(PlayParameter.PLAY_PARAM_AK_ID);
                vidSts.setAkSceret(PlayParameter.PLAY_PARAM_AK_SECRE);
                vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setVidSts(vidSts);
                }

            }
        }
    }


    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //转为竖屏了。
                //显示状态栏
                //                if (!isStrangePhone()) {
                //                    getSupportActionBar().show();
                //                }

                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int) (ScreenUtils.getWidth(getContext()) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //                if (!isStrangePhone()) {
                //                    aliVcVideoViewLayoutParams.topMargin = getSupportActionBar().getHeight();
                //                }

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!isStrangePhone()) {
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams) mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //                if (!isStrangePhone()) {
                //                    aliVcVideoViewLayoutParams.topMargin = 0;
                //                }
            }

        }
    }

    private static class MyStsListener implements VidStsUtil.OnStsResultListener {

        private WeakReference<ExhibitionDetailFragment> weakctivity;

        public MyStsListener(ExhibitionDetailFragment act) {
            weakctivity = new WeakReference<ExhibitionDetailFragment>(act);
        }

        @Override
        public void onSuccess(String vid, String akid, String akSecret, String token) {
            ExhibitionDetailFragment activity = weakctivity.get();
            if (activity != null) {
                activity.onStsSuccess(vid, akid, akSecret, token);
            }
        }

        @Override
        public void onFail() {
            ExhibitionDetailFragment activity = weakctivity.get();
            if (activity != null) {
                activity.onStsFail();
            }
        }
    }

    private void onStsFail() {

        Toast.makeText(getContext(), com.aliyun.vodplayer.R.string.request_vidsts_fail, Toast.LENGTH_LONG).show();
        inRequest = false;
        //finish();
    }

    private void onStsSuccess(String mVid, String akid, String akSecret, String token) {

        PlayParameter.PLAY_PARAM_VID = mVid;
        PlayParameter.PLAY_PARAM_AK_ID = akid;
        PlayParameter.PLAY_PARAM_AK_SECRE = akSecret;
        PlayParameter.PLAY_PARAM_SCU_TOKEN = token;

        inRequest = false;
        // 请求sts成功后, 加载播放资源,和视频列表
        setPlaySource();

    }

    private static class MyOrientationChangeListener implements AliyunVodPlayerView.OnOrientationChangeListener {

        private final WeakReference<ExhibitionDetailFragment> weakReference;

        public MyOrientationChangeListener(ExhibitionDetailFragment activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void orientationChange(boolean from, AliyunScreenMode currentMode) {
            ExhibitionDetailFragment activity = weakReference.get();

            activity.hideShowMoreDialog(from, currentMode);
        }
    }

    private void hideShowMoreDialog(boolean from, AliyunScreenMode currentMode) {
        getVu().hideShowMoreDialog(from, currentMode);
    }


    /**
     * 判断是否有网络的监听
     */
    private class MyNetConnectedListener implements AliyunVodPlayerView.NetConnectedListener {
        WeakReference<ExhibitionDetailFragment> weakReference;

        public MyNetConnectedListener(ExhibitionDetailFragment activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onReNetConnected(boolean isReconnect) {


        }

        @Override
        public void onNetUnConnected() {
            ExhibitionDetailFragment activity = weakReference.get();
            activity.onNetUnConnected();
        }
    }

    private void onNetUnConnected() {
        currentError = ErrorInfo.UnConnectInternet;

    }


    private static class MyOnUrlTimeExpiredListener implements IAliyunVodPlayer.OnUrlTimeExpiredListener {
        WeakReference<ExhibitionDetailFragment> weakReference;

        public MyOnUrlTimeExpiredListener(ExhibitionDetailFragment activity) {
            weakReference = new WeakReference<ExhibitionDetailFragment>(activity);
        }

        @Override
        public void onUrlTimeExpired(String s, String s1) {
            ExhibitionDetailFragment activity = weakReference.get();
            activity.onUrlTimeExpired(s, s1);
        }
    }

    private void onUrlTimeExpired(String oldVid, String oldQuality) {
        //requestVidSts();
        AliyunVidSts vidSts = VidStsUtil.getVidSts(oldVid);
        PlayParameter.PLAY_PARAM_VID = vidSts.getVid();
        PlayParameter.PLAY_PARAM_AK_SECRE = vidSts.getAkSceret();
        PlayParameter.PLAY_PARAM_AK_ID = vidSts.getAcId();
        PlayParameter.PLAY_PARAM_SCU_TOKEN = vidSts.getSecurityToken();

    }

    private static class MyShowMoreClickLisener implements ControlView.OnShowMoreClickListener {
        WeakReference<ExhibitionDetailFragment> weakReference;

        MyShowMoreClickLisener(ExhibitionDetailFragment activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void showMore() {
            ExhibitionDetailFragment activity = weakReference.get();
            activity.getVu().showMore(activity.getContext());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }

    }

    @Override
    public void onDestroy() {
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onDestroy();
            mAliyunVodPlayerView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        updatePlayerViewMode();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updatePlayerViewMode();
    }

    @Override
    public void share() {
        if (mShareInfo != null) {
            ShareModel shareModel = new ShareModel();
            shareModel.setContent(mShareInfo.getSubTitle());
            shareModel.setTitle(mShareInfo.getTitle());
            shareModel.setIcon(mShareInfo.getShareIcon());
            shareModel.setTargetUrl(ApiManager.getInstance().getApiConfig().getExhibitionDetail(activityid));
            ShareMenuActivity.startShare(ExhibitionDetailFragment.this,shareModel);
        } else {
            getShareinfo(true);
        }
    }

    @Override
    public void openActivityInstruction() {
        // TODO: 2018/9/21 打开活动说明 
//        start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL,"").build());
    }

    @Override
    public int getExhibitionType() {
        return exhibitionType;
    }

}
