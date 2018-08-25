package com.yufan.library.pay.alipay;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.yufan.library.pay.PayMetadata;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 */
public class ToALiPay {
    private static ToALiPay instance;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    public static ToALiPay getInstance() {
        if (instance == null) {
            instance = new ToALiPay();
        }
        return instance;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // TODO: 2018/8/25 支付成功
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            JSONObject resultJson = new JSONObject();
                            try {
                                resultJson.put("status", resultStatus);
                                resultJson.put("message", "支付结果确认中");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            // TODO: 2018/8/25 支付失败
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    JSONObject resultJson = new JSONObject();
                    try {
                        resultJson.put("status", "-1");
                        resultJson.put("message", "检查结果为：" + msg.obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public void action(final Context context, final PayMetadata payMetadata) {
        try {
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    // 构造PayTask 对象
                    PayTask alipay = new PayTask((Activity) context);
                    // 调用支付接口，获取支付结果
                    String result = alipay.pay(payMetadata.getOrderStr(), true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
