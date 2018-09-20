package com.yushi.leke;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.man.MANHitBuilders;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;
import com.lzy.okgo.OkGo;
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
import com.yufan.library.manager.UserManager;
import com.yushi.leke.util.ArgsUtil;

/**
 * Created by mengfantao on 18/2/2.
 */

public class App extends BaseApplication {
    private static App instance;

    //TODO 申请第三方开发者账号
    {
        PlatformConfig.setWeixin("wxa78cb5eacb190d7f", "e05682fc1299b50066a491633c3a9820");
        PlatformConfig.setQQZone("101501863", "6d939e10d19d2a084b31d20e87439ec6");
        PlatformConfig.setSinaWeibo("xinlangid", "xinlangsecret", "xinlanghuidiao");
    }

    public static App getApp() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
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
        ApiManager.getInstance().init(SPManager.getInstance().getInt(Global.SP_KEY_SERVICE_TYPE, BuildConfig.API_TYPE));
        //注册信鸽
        registerXGPush(UserManager.getInstance().getToken());
        initCustomPushNotificationBuilder(getApplicationContext());
        OkGo.getInstance().init(this);
        initManService();
    }
    /**
     * 初始化Mobile Analytics服务
    */
    private void initManService() {
        // 获取MAN服务
        MANService manService = MANServiceProvider.getService();
        // 打开调试日志
        manService.getMANAnalytics().turnOnDebug();
        manService.getMANAnalytics().setAppVersion("3.0");
        // MAN初始化方法之一，通过插件接入后直接在下发json中获取appKey和appSecret初始化
        manService.getMANAnalytics().init(this, getApplicationContext());
        // MAN另一初始化方法，手动指定appKey和appSecret
        // String appKey = "******";
        // String appSecret = "******";
        // manService.getMANAnalytics().init(this, getApplicationContext(), appKey, appSecret);
        // 若需要关闭 SDK 的自动异常捕获功能可进行如下操作,详见文档5.4
        //manService.getMANAnalytics().turnOffCrashReporter();
        // 通过此接口关闭页面自动打点功能，详见文档4.2
        manService.getMANAnalytics().turnOffAutoPageTrack();
        // 设置渠道（用以标记该app的分发渠道名称），如果不关心可以不设置即不调用该接口，渠道设置将影响控制台【渠道分析】栏目的报表展现。如果文档3.3章节更能满足您渠道配置的需求，就不要调用此方法，按照3.3进行配置即可
        //manService.getMANAnalytics().setChannel("某渠道");
        // 若AndroidManifest.xml 中的 android:versionName 不能满足需求，可在此指定；
        // 若既没有设置AndroidManifest.xml 中的 android:versionName，也没有调用setAppVersion，appVersion则为null
        //manService.getMANAnalytics().setAppVersion("2.0");
        ArgsUtil.datapoint(ArgsUtil.APP_START,"null",ArgsUtil.UID,ArgsUtil.STARTCODE,null,null);
    }
    public void registerXGPush(String uid) {
        if (TextUtils.isEmpty(uid)){
            uid = "*";
        }
        XGPushManager.registerPush(getApplicationContext(), uid,
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
