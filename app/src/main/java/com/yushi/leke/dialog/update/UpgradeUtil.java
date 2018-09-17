package com.yushi.leke.dialog.update;

import android.content.Context;
import android.util.Log;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.ApkUtils;

import java.io.File;

/**
 * 作者：Created by zhanyangyang on 2018/9/13 16:21
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class UpgradeUtil {
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
                        }catch (Exception e){
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
