package com.yushi.leke.util;

import com.alibaba.sdk.android.man.MANHitBuilders;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;

import java.util.Map;

public class ArgsUtil {
    public static ArgsUtil instance;
    private MANService manService;

    private ArgsUtil() {
        manService = MANServiceProvider.getService();
    }

    public static ArgsUtil getInstance() {
        if (instance == null) {
            instance = new ArgsUtil();
        }
        return instance;
    }

    public void datapoint(String eventName, Map<String, String> params) {
        MANHitBuilders.MANCustomHitBuilder hitBuilder = new MANHitBuilders.MANCustomHitBuilder(eventName);
        if (params!=null){
            for (String key : params.keySet()) {
                hitBuilder.setProperty(key, params.get(key));
            }
        }
        manService.getMANAnalytics().getDefaultTracker().send(hitBuilder.build());
    }

    // 注册用户埋点
    public void registerpoint(String uid) {
        manService.getMANAnalytics().userRegister(uid);
    }
}
