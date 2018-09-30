package com.yushi.leke.fragment.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.UserManager;
import com.yufan.library.widget.anim.AFVerticalAnimator;
import com.yushi.leke.App;
import com.yushi.leke.R;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.update.UpdateDialog;
import com.yushi.leke.dialog.update.UpdateInfo;
import com.yushi.leke.dialog.update.UpgradeUtil;
import com.yushi.leke.fragment.album.AlbumDetailFragment;
import com.yushi.leke.fragment.browser.BrowserBaseFragment;
import com.yushi.leke.fragment.exhibition.detail.ExhibitionDetailFragment;
import com.yushi.leke.fragment.home.SubscriptionsFragment;
import com.yushi.leke.fragment.exhibition.exhibitionHome.ExhibitionFragment;
import com.yushi.leke.fragment.login.LoginFragment;
import com.yushi.leke.fragment.splash.advert.NativeJumpInfo;
import com.yushi.leke.fragment.test.TestListFragment;
import com.yushi.leke.fragment.ucenter.UCenterFragment;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(MainVu.class)
public class MainFragment extends BaseFragment<MainContract.IView> implements MainContract.Presenter {
    private SupportFragment[] mFragments = new SupportFragment[3];
    private long[] mHits = new long[2];


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Global.BROADCAST_TOKEN_LOSE://token失效
                    UserManager.getInstance().setToken("");
                    UserManager.getInstance().setUid("");
                    App.getApp().bindUid("*");
                    getRootFragment().startWithPopTo(UIHelper.creat(LoginFragment.class).build(), MainFragment.class, true);
                    break;
                case Global.BROADCAST_ACTION_ADJUMP://广告具体跳转
                    String h5Url = intent.getStringExtra("h5Url");
                    NativeJumpInfo nativeJumpInfo = (NativeJumpInfo) intent.getSerializableExtra("nativeUrl");
                    if (nativeJumpInfo != null) {//0：活动 1：专辑
                        if (TextUtils.equals("0", nativeJumpInfo.getDetailType())) {
                            getRootFragment().start(UIHelper.creat(AlbumDetailFragment.class).put(Global.BUNDLE_KEY_ALBUMID, nativeJumpInfo.getDetailId()).build());
                        } else if (TextUtils.equals("1", nativeJumpInfo.getDetailType())) {
                            if (nativeJumpInfo.getActivityProgress() == 0 || nativeJumpInfo.getActivityProgress() == 1) {//h5详情页面
                                getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getExhibitionDetail(nativeJumpInfo.getDetailId())).build());
                            } else {//原生详情页面
                                getRootFragment().start(UIHelper.creat(ExhibitionDetailFragment.class)
                                        .put(Global.BUNDLE_KEY_EXHIBITION_TYE, nativeJumpInfo.getActivityProgress())
                                        .put(Global.BUNDLE_KEY_ACTIVITYID, nativeJumpInfo.getDetailId())
                                        .build());
                            }
                        }
                    } else {
                        if (!TextUtils.isEmpty(h5Url)) {
                            start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, h5Url).build());
                        }
                    }
                    break;
                case Global.BROADCAST_ACTION_UPGRADE:
                    UpgradeUtil.checkAppUpdate(_mActivity);
                    break;
                case Global.BROADCAST_ACTION_XGMESSAGE:
                    int type = intent.getIntExtra("appMsgType",1);
                    if (type==0){
                        type=2;
                    }
                    getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMessage(type)).build());
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Global.BROADCAST_TOKEN_LOSE);
        intentFilter.addAction(Global.BROADCAST_ACTION_ADJUMP);
        intentFilter.addAction(Global.BROADCAST_ACTION_UPGRADE);
        intentFilter.addAction(Global.BROADCAST_ACTION_XGMESSAGE);
        LocalBroadcastManager.getInstance(_mActivity).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(_mActivity).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportFragment firstFragment = findChildFragment(TestListFragment.class);
        if (firstFragment == null) {
            mFragments[0] = UIHelper.creat(SubscriptionsFragment.class).build();
            mFragments[1] = UIHelper.creat(ExhibitionFragment.class).build();
            mFragments[2] = UIHelper.creat(UCenterFragment.class).build();
            loadMultipleRootFragment(R.id.realtabcontent, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(ExhibitionFragment.class);
            mFragments[2] = findChildFragment(UCenterFragment.class);
        }
    }

    public void hasUnreadMsg(boolean hasUnreadMsg) {
        getVu().hasUnreadMsg(hasUnreadMsg);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            boolean isAll = data.getBoolean("isAll");
            updatePersonInfo(isAll);
        }
    }

    public void updatePersonInfo(boolean isAll) {
        if (mFragments[2] != null && mFragments[2] instanceof UCenterFragment) {
            ((UCenterFragment) mFragments[2]).updatePersonInfo(isAll);
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressedSupport() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            //退出
            return super.onBackPressedSupport();
        } else {
            //提示
            Toast.makeText(_mActivity, "再次点击退出", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new AFVerticalAnimator(); //super.onCreateFragmentAnimator();
    }


    @Override
    public void switchTab(int formPositon, int toPosition) {
        showHideFragment(mFragments[toPosition], mFragments[formPositon]);
    }
}
