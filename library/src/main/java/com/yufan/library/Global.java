package com.yufan.library;

/**
 * Created by mengfantao on 18/7/26.
 */

public class Global {
    /**
     * 浏览器页面url
     */
    public static final String BUNDLE_KEY_BROWSER_URL = "BUNDLE_KEY_BROWSER_URL";
    public static final String SP_KEY_SERVICE_TYPE = "SP_KEY_SERVICE_TYPE";
    /**
     * 新手指引
     */
    public static final String SP_KEY_NEW_GUIDE= "SP_KEY_NEW_GUIDE";
    public static final String ARG_MEDIA_ID = "media_id";
    /**
     * 支付结果通知广播action
     */
    public static final String BROADCAST_PAY_RESUIL_ACTION = "BROADCAST_PAY_RESUIL_ACTION";
    /**
     * 支付结果返回data true:成功 false:失败
     */
    public static final String INTENT_PAY_RESUIL_DATA = "INTENT_PAY_RESUIL_DATA";

    public static final String BIND_PHONE_TYPE_KEY = "BIND_PHONE_TYPE_KEY";
    public static final int BIND_PHONE_FROM_SETPWD = 1;//从设置密码跳转到绑定手机
    public static final int BIND_PHONE_FROM_FORGETPWD = 2;//从忘记密码跳转到绑定手机
    public static final int BIND_PHONE_FROM_PAY = 3;//从支付跳转到绑定手机
}
