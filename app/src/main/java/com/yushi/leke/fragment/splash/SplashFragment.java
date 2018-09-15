package com.yushi.leke.fragment.splash;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.cache.CacheManager;
import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.SPManager;
import com.yufan.library.util.SIDUtil;
import com.yufan.library.util.YFUtil;
import com.yushi.leke.R;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.splash.advert.AdFragmentFragment;
import com.yushi.leke.fragment.splash.advert.AdInfo;
import com.yushi.leke.fragment.splash.guide.GuideFragmentFragment;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SplashVu.class)
public class SplashFragment extends BaseFragment<SplashContract.IView> implements SplashContract.Presenter, EasyPermissions.PermissionCallbacks {
    private static final int RC_LOCATION = 201;
    private Handler handler = new Handler();
    private AdInfo adInfo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMapLocation();
        init();
        getAdInfo();
    }

    //初始化定位
    private void initMapLocation() {
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(_mActivity, perms)) {
            //有权限
            jumpToMain();
        } else {
            //没有权限
            EasyPermissions.requestPermissions(this, "请求获取卫星定位权限",
                    RC_LOCATION, perms);
        }
    }


    /**
     * 初始化接口
     */
    private void init() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).init()).enqueue(new BaseHttpCallBack() {
            @Override
            public void onResponse(ApiBean mApiBean) {
                if (ApiBean.checkOK(mApiBean.getCode())) {
                    JSONObject jsonObject = JSON.parseObject(mApiBean.data);
                    String sid = jsonObject.getString("sid");
                    SIDUtil.setSID(getContext(), sid);
                }
            }

            @Override
            public void onFailure(int id, Exception e) {
            }

            @Override
            public void onSuccess(ApiBean mApiBean) {

            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void getAdInfo() {
        testInfo();

        String adKey = SPManager.getInstance().getString(Global.SP_AD_KEY, "");
        if (!TextUtils.isEmpty(adKey) && CacheManager.isExistDataCache(_mActivity, adKey)) {//广告缓存存在，直接展示
            Serializable serializable = CacheManager.readObject(_mActivity, adKey);
            if (serializable != null && serializable instanceof AdInfo) {
                adInfo = (AdInfo) serializable;
            }
        }
        //有没有广告缓存，都发起网络请求，请求最新广告信息并且缓存，供下次启动备用
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).initAd()).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onResponse(ApiBean mApiBean) {
                super.onResponse(mApiBean);
                if (ApiBean.checkOK(mApiBean.getCode())) {
                    if (TextUtils.isEmpty(mApiBean.getData())) {
                        final AdInfo mAdInfo = JSON.parseObject(mApiBean.getData(), AdInfo.class);
                        if (mAdInfo != null) {
                            if (!CacheManager.isExistDataCache(_mActivity, mAdInfo.getAdKey())) {
                                Glide.with(_mActivity).load(mAdInfo.getAdImgurl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        resource.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                                        mAdInfo.setBitmap(baos.toByteArray());
                                        CacheManager.saveObject(_mActivity, mAdInfo, mAdInfo.getAdKey());
                                        SPManager.getInstance().saveValue(Global.SP_AD_KEY, mAdInfo.getAdKey());
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(int id, Exception e) {
            }

            @Override
            public void onSuccess(ApiBean mApiBean) {
            }

            @Override
            public void onError(int id, Exception e) {
            }

            @Override
            public void onFinish() {
            }
        });
    }


    private void testInfo() {
        final AdInfo temp = new AdInfo();
        temp.setAdImgurl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536992413846&di=4def7b1e989446a27151adee9afd158e&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201409%2F13%2F20140913140805_EZYKn.jpeg");
        temp.setActionUrl("https://www.baidu.com");
        temp.setAdKey("广告");
        if (!CacheManager.isExistDataCache(_mActivity, temp.getAdKey())) {
            Glide.with(_mActivity).load(temp.getAdImgurl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    resource.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    temp.setBitmap(baos.toByteArray());
                    CacheManager.saveObject(_mActivity, temp, temp.getAdKey());
                    SPManager.getInstance().saveValue(Global.SP_AD_KEY, temp.getAdKey());
                }
            });
        }
    }


    private void jumpToMain() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(SplashFragment.this).commit();
                if (!SPManager.getInstance().getBoolean(Global.SP_GUIDE_KEY + YFUtil.getVersionName(), false)) {
                    UIHelper.openFragment(_mActivity, UIHelper.creat(GuideFragmentFragment.class).build(), false);
                    SPManager.getInstance().saveValue(Global.SP_GUIDE_KEY + YFUtil.getVersionName(), true);
                } else {
                    if (adInfo != null && !TextUtils.isEmpty(adInfo.getAdKey())) {
                        UIHelper.openFragment(_mActivity, UIHelper.creat(AdFragmentFragment.class).put("adKey", adInfo.getAdKey()).build(), false);
                    } else {
                        if (_mActivity != null && !_mActivity.isFinishing()) {
                            WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
                            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            _mActivity.getWindow().setAttributes(lp);
                            _mActivity.getWindow().setBackgroundDrawableResource(R.color.white);
                        }
                    }
                }
            }
        }, 1000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == RC_LOCATION) {
            jumpToMain();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        jumpToMain();
    }


    @Override
    public void onRefresh() {
    }
}
