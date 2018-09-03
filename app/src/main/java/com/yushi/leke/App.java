package com.yushi.leke;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.common.Constants;
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
        PlatformConfig.setQQZone("1107596034", "R7Fw2IprSaHgsQgb");
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
        XGPushConfig.enableDebug(this, true);
        QbSdk.initX5Environment(getApplicationContext(), null);
        ApiManager.getInstance().init(SPManager.getInstance().getInt(Global.SP_KEY_SERVICE_TYPE,BuildConfig.API_TYPE));
    //注册信鸽
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.w(Constants.LogTag, "+++ register push sucess. token:" + data + "flag" + flag);

                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.w(Constants.LogTag,
                                "+++ register push fail. token:" + data
                                        + ", errCode:" + errCode + ",msg:"
                                        + msg);

                    }
                });
        initCustomPushNotificationBuilder(getApplicationContext());

    }

    /**
     * 设置通知自定义View，这样在下发通知时可以指定build_id。编号由开发者自己维护,build_id=0为默认设置
     *
     * @param context
     */
    @SuppressWarnings("unused")
    private void initCustomPushNotificationBuilder(Context context) {
        XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();
        int id = context.getResources().getIdentifier(
                "tixin", "raw", context.getPackageName());
        String uri = "android.resource://"
                + context.getPackageName() + "/" + id;
        build.setSound(Uri.parse(uri));
        // 设置自定义通知layout,通知背景等可以在layout里设置
        build.setLayoutId(R.layout.xg_notification);
        // 设置自定义通知内容id
        build.setLayoutTextId(R.id.content);
        // 设置自定义通知标题id
        build.setLayoutTitleId(R.id.title);
        // 设置自定义通知图片id
        build.setLayoutIconId(R.id.icon);
        // 设置自定义通知图片资源
        build.setLayoutIconDrawableId(R.drawable.ic_launcher);
        // 设置状态栏的通知小图标
        //build.setbigContentView()
        build.setIcon(R.drawable.ic_launcher);
        // 设置时间id
        build.setLayoutTimeId(R.id.time);
        // 若不设定以上自定义layout，又想简单指定通知栏图片资源
        //build.setNotificationLargeIcon(R.drawable.ic_action_search);
        // 客户端保存build_id
        XGPushManager.setPushNotificationBuilder(this, 999, build);
        XGPushManager.setDefaultNotificationBuilder(this, build);
    }



}
