package com.yushi.leke.dialog.update;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.ApkUtils;
import com.yushi.leke.YFApi;

import java.io.File;

/**
 * 作者：Created by zhanyangyang on 2018/9/13 16:21
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class UpgradeUtil {

    /**
     * 检查更新
     */
    public static void checkAppUpdate(final Activity activity) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).checkAppUpdate()).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    UpdateInfo updateInfo = JSON.parseObject(mApiBean.getData(), UpdateInfo.class);
                    showUpdateDialog(updateInfo, activity);
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


    private static void showUpdateDialog(UpdateInfo updateInfo, final Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        if (updateInfo != null && updateInfo.isNeedUpdate()) {
            UpdateDialog updateDialog = new UpdateDialog(activity, updateInfo, new ICallBack() {
                @Override
                public void OnBackResult(Object... s) {
                    //退出app
                    if (activity != null && !activity.isFinishing()) {
                        activity.finish();
                    }
                }
            });
            updateDialog.show();
        }

    }


    public static void upgrade(final Context context, String updataurl) {
        DialogManager.getInstance().toast("更新中，请稍等");
        OkDownload.getInstance().removeAll(true);
        GetRequest request = new GetRequest(updataurl);
        OkDownload.request(updataurl, request)
                .priority(1)
                .save()
                .register(new DownloadListener(updataurl) {
                    @Override
                    public void onStart(Progress progress) {
                        Log.e("UpdateDialog", "onStart");
                    }

                    @Override
                    public void onProgress(Progress progress) {
                        Log.e("UpdateDialog", "onProgress" + (progress.currentSize / progress.totalSize) * 100);
                    }

                    @Override
                    public void onError(Progress progress) {
                        Log.e("UpdateDialog", "onError");
                    }

                    @Override
                    public void onFinish(File file, Progress progress) {
                        Log.e("UpdateDialog", "onFinish");
                        try {
                            ApkUtils.install(context, file);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onRemove(Progress progress) {
                        Log.e("UpdateDialog", "onRemove");
                    }
                })
                .start();
    }
}
