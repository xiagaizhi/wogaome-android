package com.yushi.leke.util;

import com.alibaba.sdk.android.man.MANHitBuilders;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;

public class ArgsUtil {
    //重载方法，参数不同
    public static void datapoint(String eventname,String arg1,String value1){
        MANHitBuilders.MANCustomHitBuilder hitBuilder = new MANHitBuilders.MANCustomHitBuilder(eventname);
        if (value1!=null){
            hitBuilder.setProperty(arg1, value1);
        }
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().getDefaultTracker().send(hitBuilder.build());
    }
    public static void datapoint(String eventname,String arg1,String value1,String arg2,String value2){
        MANHitBuilders.MANCustomHitBuilder hitBuilder = new MANHitBuilders.MANCustomHitBuilder(eventname);
        if (value1!=null){
            hitBuilder.setProperty(arg1, value1);
        }
        if (value2!=null){
            hitBuilder.setProperty(arg2, value2);
        }
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().getDefaultTracker().send(hitBuilder.build());
    }
    public static void datapoint(String eventname,String arg1,String value1,String arg2,String value2,String arg3,String value3){
        MANHitBuilders.MANCustomHitBuilder hitBuilder = new MANHitBuilders.MANCustomHitBuilder(eventname);
        if (value1!=null){
            hitBuilder.setProperty(arg1, value1);
        }
        if (value2!=null){
            hitBuilder.setProperty(arg2, value2);
        }
        if (value3!=null){
            hitBuilder.setProperty(arg3, value3);
        }
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().getDefaultTracker().send(hitBuilder.build());
    }
    // 注册用户埋点
    public static void registerpoint(String uid){
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().userRegister(uid);
    }

    public static ArgsUtil instance;
    public static ArgsUtil ArgsUtil(){
        instance=new ArgsUtil();
        return instance;
    }

    public static ArgsUtil getInstance() {
        return instance;
    }

    public static void setInstance(ArgsUtil instance) {
        ArgsUtil.instance = instance;
    }
}
