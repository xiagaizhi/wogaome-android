package com.yushi.leke.util;

import com.alibaba.sdk.android.man.MANHitBuilders;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;

public class ArgsUtil {
    public static final String ARG1="arg1";
    public static final String ARG2="arg2";
    public static final String ARG3="arg3";
    public static final String ARGS="args";
    public static final String UID="uid";
    public static final String APP_START="app_start";
    public static final String STARTCODE="0100";
    public static final String REG_PHONE_NAME="regigster_phone";
    public static final String REG_PHONE_CODE="0201";
    public static final String REG_WX_NAME="regigster_weixin";
    public static final String REG_WX_CODE="0202";
    public static final String LOGIN_PHONE_NAME="login_phone";
    public static final String LOGIN_PHONE_CODE="0301";
    public static final String VOTE_NAME="vote";
    public static final String VOTE_CODE="0090";
    public static void datapoint(String eventname,String associatname,String value1,String value2,String value3,String values){
        MANHitBuilders.MANCustomHitBuilder hitBuilder = new MANHitBuilders.MANCustomHitBuilder(eventname);
        hitBuilder.setEventPage(associatname);
        if (value1!=null){
            hitBuilder.setProperty(ArgsUtil.ARG1, value1);
        }
        if (value2!=null){
            hitBuilder.setProperty(ArgsUtil.ARG2, value2);
        }
        if (value3!=null){
            hitBuilder.setProperty(ArgsUtil.ARG3, value3);
        }
        if (values!=null){
            hitBuilder.setProperty(ArgsUtil.ARGS, values);
        }
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().getDefaultTracker().send(hitBuilder.build());
    }
    public static ArgsUtil instance;
    private ArgsUtil(){

    }
    public static ArgsUtil getInstance(){
        if (instance == null) {
            instance = new ArgsUtil();
        }
        return instance;
    }
}
