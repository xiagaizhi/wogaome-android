package com.yufan.library.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.BuildConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.Bugly;
import com.umeng.commonsdk.UMConfigure;
import com.yufan.library.util.AppException;

import java.util.Iterator;
import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by mengfantao on 18/2/2.
 */

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 防止第三方服务多次执行．
         */
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            return;
        }
        Thread.setDefaultUncaughtExceptionHandler(new AppException());
        instance = this;
        if (!BuildConfig.DEBUG) {
            Bugly.init(getApplicationContext(), "f01636373e16a", false);
        }

        UMConfigure.init(this, "5b6bafa0b27b0a7d06000033", "leke", UMConfigure.DEVICE_TYPE_PHONE, "");
        Fresco.initialize(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }


    public String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {

            }
        }
        return processName;
    }

}
