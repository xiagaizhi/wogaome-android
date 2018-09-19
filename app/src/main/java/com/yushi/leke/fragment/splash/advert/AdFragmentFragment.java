package com.yushi.leke.fragment.splash.advert;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.yufan.library.Global;
import com.yufan.library.base.BaseApplication;
import com.yufan.library.cache.CacheManager;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.browser.BrowserBaseFragment;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(AdFragmentVu.class)
public class AdFragmentFragment extends BaseFragment<AdFragmentContract.IView> implements AdFragmentContract.Presenter {
    private String adKey;
    private Bitmap bitmap;
    private AdInfo adInfo;
    private boolean isJumpTomain;
    private int currentSeconds = 3;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x100:
                    if (currentSeconds >= 2) {
                        currentSeconds--;
                        getVu().updateCountdown(currentSeconds);
                        mHandler.sendEmptyMessageDelayed(0x100, 1000);
                    } else {
                        jumpToMain();
                        mHandler.removeMessages(0x100);
                    }
                    break;
                case 0x200:
                    jumpToMain();
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            adKey = bundle.getString(Global.BUNDLE_AD_KEY);
            if (!TextUtils.isEmpty(adKey)) {
                adInfo = (AdInfo) CacheManager.readObject(_mActivity, adKey);
                if (adInfo != null) {
                    bitmap = BitmapFactory.decodeByteArray(adInfo.getBitmap(), 0, adInfo.getBitmap().length);
                    getVu().updateAd(adInfo, bitmap);
                } else {
                    jumpToMain();
                }
            } else {
                jumpToMain();
            }
        } else {
            jumpToMain();
        }
        mHandler.sendEmptyMessageDelayed(0x100, 1000);
    }

    @Override
    public void jumpToMain() {
        if (!isJumpTomain) {
            if (_mActivity != null && !_mActivity.isFinishing()) {
                WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
                lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                _mActivity.getWindow().setAttributes(lp);
                _mActivity.getWindow().setBackgroundDrawableResource(R.color.white);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(AdFragmentFragment.this).commit();
                Intent filter = new Intent();
                filter.setAction(Global.BROADCAST_ACTION_UPGRADE);
                LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(filter);
            }
            isJumpTomain = true;
        }
    }

    @Override
    public void openAdDetail() {
        //打开广告具体跳转页面
        Intent filter = new Intent();
        filter.setAction(Global.BROADCAST_ACTION_ADJUMP);
        filter.putExtra("h5Url", adInfo.getH5Url());
        filter.putExtra("nativeUrl",adInfo.getNativeUrl());
        LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(filter);
        //关闭广告页面
        mHandler.sendEmptyMessageDelayed(0x200,100);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }


    @Override
    public void onRefresh() {

    }
}
