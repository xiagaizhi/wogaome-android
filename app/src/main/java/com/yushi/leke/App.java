package com.yushi.leke;

import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.PlatformConfig;
import com.yufan.library.Global;
import com.yufan.library.api.ApiManager;
import com.yufan.library.base.BaseApplication;
import com.yufan.library.manager.SPManager;

/**
 * Created by mengfantao on 18/2/2.
 */

public class App extends BaseApplication {
    private static App instance;
    //TODO 申请第三方开发者账号
    {
        PlatformConfig.setWeixin("wxa78cb5eacb190d7f", "e05682fc1299b50066a491633c3a9820");
        PlatformConfig.setQQZone("qqid", "qqsecret");
        PlatformConfig.setSinaWeibo("xinlangid", "xinlangsecret", "xinlanghuidiao");
    }
    public static App getApp(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        /**
         * 防止第三方服务多次执行．
         */
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e("service", "enter the service process!");
            return;
        }
        QbSdk.initX5Environment(getApplicationContext(), null);
        ApiManager.getInstance().init(SPManager.getInstance().getInt(Global.SP_KEY_SERVICE_TYPE,BuildConfig.API_TYPE));
    }




}
