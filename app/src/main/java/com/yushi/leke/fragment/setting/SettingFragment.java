package com.yushi.leke.fragment.setting;

import android.os.AsyncTask;
import android.os.Bundle;

import com.yufan.library.cache.DataCleanManager;
import com.yufan.library.manager.UserManager;
import com.yufan.library.util.FileUtil;
import com.yufan.library.util.MethodsCompat;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.dialog.CommonDialog;
import com.yushi.leke.fragment.login.LoginFragment;
import com.yushi.leke.fragment.main.MainFragment;

import java.io.File;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(SettingVu.class)
public class SettingFragment extends BaseFragment<SettingContract.IView> implements SettingContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        caculateCacheSize();

    }


    @Override
    public void onRefresh() {

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
        } else {
            cacheSize = "0kB";
        }
        getVu().updataCacheSize(cacheSize);
    }


    @Override
    public void cleanMemoryCache() {
        new CommonDialog(getContext())
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
        UserManager.getInstance().setToken("");
        UserManager.getInstance().setUid("");
        getRootFragment().startWithPopTo(UIHelper.creat(LoginFragment.class).build(), MainFragment.class, true);
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void lekeAbout() {

    }

    @Override
    public void accountAndSafety() {

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
