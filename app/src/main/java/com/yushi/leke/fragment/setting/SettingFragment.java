package com.yushi.leke.fragment.setting;

import android.os.AsyncTask;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.cache.DataCleanManager;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.UserManager;
import com.yufan.library.util.FileUtil;
import com.yufan.library.util.MethodsCompat;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.App;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;
import com.yushi.leke.dialog.update.UpdateDialog;
import com.yushi.leke.dialog.update.UpdateInfo;
import com.yushi.leke.dialog.update.UpgradeUtil;
import com.yushi.leke.fragment.exhibition.vote.WinlistDialogFragment;
import com.yushi.leke.fragment.login.LoginFragment;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.fragment.setting.AccountAndSafety.AccountAndSafetyFragment;
import com.yushi.leke.fragment.setting.about.AboutLekeFragment;

import java.io.File;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(SettingVu.class)
public class SettingFragment extends BaseFragment<SettingContract.IView> implements SettingContract.Presenter {
    private UpdateInfo updateInfo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        caculateCacheSize();
        checkAppUpdate();
    }


    @Override
    public void onRefresh() {

    }


    /**
     * 检查更新
     */
    private void checkAppUpdate() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).checkAppUpdate())
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            updateInfo = JSON.parseObject(mApiBean.getData(), UpdateInfo.class);
                            if (updateInfo != null) {
                                getVu().upDateVersion(updateInfo);
                            }
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }


    /**
     * 计算缓存的大小
     */
    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            File externalCacheDir = MethodsCompat.getExternalCacheDir(getActivity());
            fileSize += FileUtil.getDirSize(externalCacheDir);
        }
        if (fileSize > 0) {
            cacheSize = FileUtil.formatFileSize(fileSize);
        }
        getVu().updataCacheSize(cacheSize);
    }


    @Override
    public void cleanMemoryCache() {
        new CommonDialog(_mActivity)
                .setTitle("是否清空缓存？")
                .setPositiveName("确定")
                .setNegativeName("取消")
                .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                    @Override
                    public void onClick(CommonDialog commonDialog, int actionType) {
                        if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    clearAppCache();
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                    getVu().updataCacheSize("0KB");
                                }
                            }.execute();
                        }
                        commonDialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void logout() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).logout()).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onResponse(ApiBean mApiBean) {
//                super.onResponse(mApiBean);
            }

            @Override
            public void onFailure(int id, Exception e) {
//                super.onFailure(id, e);
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

        UserManager.getInstance().setToken("");
        UserManager.getInstance().setUid("");
        App.getApp().registerXGPush("*");
        getRootFragment().startWithPopTo(UIHelper.creat(LoginFragment.class).build(), MainFragment.class, true);
    }

    @Override
    public void upgrade() {
        if (updateInfo != null && updateInfo.isNeedUpdate()) {
            UpgradeUtil.upgrade(_mActivity,updateInfo.getPkgPath());
        }
    }

    @Override
    public void lekeAbout() {
        start(UIHelper.creat(AboutLekeFragment.class).build());
    }

    @Override
    public void accountAndSafety() {
        start(UIHelper.creat(AccountAndSafetyFragment.class).build());
    }

    @Override
    public void openPlayer() {

    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(_mActivity);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            DataCleanManager.cleanCustomCache(MethodsCompat.getExternalCacheDir(_mActivity));
        }
        // 清除编辑器保存的临时内容
//        Properties props = getProperties();
//        for (Object key : props.keySet()) {
//            String _key = key.toString();
//            if (_key.startsWith("temp"))
//                AppConfig.getAppConfig(AppContext.getInstance()).remove(_key);
//        }

    }
}
