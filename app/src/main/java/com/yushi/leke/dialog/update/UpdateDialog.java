package com.yushi.leke.dialog.update;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.ApkUtils;
import com.yushi.leke.R;

import java.io.File;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * 作者：Created by zhanyangyang on 2018/9/12 19:37
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class UpdateDialog extends Dialog {
    private UpdateInfo mUpdateInfo;
    private ICallBack mICallBack;

    public UpdateDialog(@NonNull Context context, UpdateInfo updateInfo, ICallBack callBack) {
        super(context);
        this.mUpdateInfo = updateInfo;
        this.mICallBack = callBack;
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_update_layout, null);
        setContentView(rootView);
        initView(rootView);
        setCanceledOnTouchOutside(false);
    }

    private void initView(View view) {

    }

    public static void toUpDate(String updataurl) {
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
                        ApkUtils.install(context, file);
                    }

                    @Override
                    public void onRemove(Progress progress) {
                        Log.e("UpdateDialog", "onRemove");
                    }
                })
                .start();
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setType(WindowManager.LayoutParams.TYPE_APPLICATION);
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        window.setAttributes(p);
        window.setDimAmount(0.6f);
    }
}
