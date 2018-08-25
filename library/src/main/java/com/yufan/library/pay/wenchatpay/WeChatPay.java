package com.yufan.library.pay.wenchatpay;

import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yufan.library.pay.PayMetadata;
import com.yufan.library.util.ToastUtil;


public class WeChatPay {
    private static WeChatPay weChatPay;
    PayReq req;
    private IWXAPI api;
    static String appid = null;

    public static WeChatPay getInstance() {
        if (weChatPay == null) {
            weChatPay = new WeChatPay();
        }
        return weChatPay;
    }

    public void toWeChatPay(Context context, PayMetadata payMetadata) {
        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
        Toast.makeText(context, "微信初始化中...", Toast.LENGTH_SHORT).show();
        try {
            appid = payMetadata.getAppId();
            msgApi.registerApp(appid);
            api = WXAPIFactory.createWXAPI(context, appid);
            if (api.isWXAppInstalled()) {
                req = new PayReq();
                req.appId = appid;
                req.partnerId = payMetadata.getPartnerId();
                req.prepayId = payMetadata.getPrepayId();
                req.packageValue = payMetadata.getPackageValue();
                req.nonceStr = payMetadata.getNonceStr();
                req.timeStamp = payMetadata.getTimeStamp();
                req.sign = payMetadata.getSign();
                sendPayReq();
            } else {
                ToastUtil.normal(context, "请安装微信再进行支付", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPayReq() {
        api.sendReq(req);
    }
}
