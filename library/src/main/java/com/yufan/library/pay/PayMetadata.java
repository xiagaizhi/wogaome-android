package com.yufan.library.pay;

/**
 * 作者：Created by zhanyangyang on 2018/8/25 14:35
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class PayMetadata {
    /**
     * 微信返回
     */
    private String appId;    //应用ID
    private String partnerId;//商户ID
    private String prepayId;//预支付ID
    private String packageValue;//包值
    private String nonceStr;//随机数
    private String timeStamp;//时间按照10位秒返回
    private String sign;

    /**
     * 支付宝返回
     */
    private String orderStr;
    private String webOrderSt;//安卓不需要

    public String getWebOrderSt() {
        return webOrderSt;
    }

    public void setWebOrderSt(String webOrderSt) {
        this.webOrderSt = webOrderSt;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}

