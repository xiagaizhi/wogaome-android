package com.yushi.leke.dialog.recharge;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.pay.PayMetadata;
import com.yufan.library.pay.alipay.ToALiPay;
import com.yufan.library.pay.wenchatpay.WeChatPay;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;

import org.json.JSONObject;

/**
 * 作者：Created by zhanyangyang on 2018/8/31 16:06
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class RechargeUtil {
    public static RechargeUtil instance;

    private RechargeUtil() {
    }

    public static RechargeUtil getInstance() {
        if (instance == null) {
            instance = new RechargeUtil();
        }
        return instance;
    }

    /**
     * 校验支付密码  不清楚是否设置过有交易密码
     */
    public void checkRechargePwd(final Context context, final String extMsg, CheckRechargePwdInterf checkRechargeInterf) {
        checkRechargePwd(0, context, extMsg, checkRechargeInterf);
    }

    /**
     * @param isHavePwd           是否有交易密码
     * @param context
     * @param extMsg
     * @param checkRechargeInterf
     */
    public void checkRechargePwd(int isHavePwd, final Context context, final String extMsg, final CheckRechargePwdInterf checkRechargeInterf) {
        if (isHavePwd == 1) {//拥有交易密码
            CheckRechargePwdDialog checkRechargePwdDialog = new CheckRechargePwdDialog(context, extMsg, checkRechargeInterf);
            checkRechargePwdDialog.show();
        } else {
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).haveTradePwd("v1", "9999"))
                    .useCache(false)
                    .enqueue(new BaseHttpCallBack() {
                        @Override
                        public void onSuccess(ApiBean mApiBean) {
                            try {
                                if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                                    String data = mApiBean.getData();
                                    JSONObject jsonObject = new JSONObject(data);
                                    int isHave = jsonObject.optInt("isHave");
                                    String phoneNumber = jsonObject.optString("phoneNumber");
                                    if (!TextUtils.isEmpty(phoneNumber)) {//绑定手机
                                        if (isHave == 1) {//拥有交易密码,验证交易密码
                                            CheckRechargePwdDialog checkRechargePwdDialog = new CheckRechargePwdDialog(context, extMsg, checkRechargeInterf);
                                            checkRechargePwdDialog.show();
                                        } else {//没有交易密码
                                            setRechargePwd(context, SetRechargePwdDialog.SET_RECHARGE_PWD, null);
                                        }
                                    } else {//未绑定过手机
                                        new CommonDialog(context).setTitle("您尚未绑定手机，请先绑定安全手机！")
                                                .setNegativeName("取消")
                                                .setPositiveName("去绑定")
                                                .setHaveNegative(true)
                                                .setCommonDialogCanceledOnTouchOutside2(false)
                                                .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                                                    @Override
                                                    public void onClick(CommonDialog commonDialog, int actionType) {
                                                        commonDialog.dismiss();
                                                        if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {
                                                            if (checkRechargeInterf != null) {
                                                                checkRechargeInterf.openBindPhone();//打开绑定手机
                                                            }
                                                        }
                                                    }
                                                }).show();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(int id, Exception e) {

                        }

                        @Override
                        public void onFinish() {

                        }
                    });
        }

    }


    /**
     * 设置交易密码
     *
     * @param context
     * @param type
     * @param setRechargeInterf
     */
    public void setRechargePwd(Context context, int type, SetRechargeInterf setRechargeInterf) {
        SetRechargePwdDialog setRechargePwdDialog = new SetRechargePwdDialog(context, type);
        setRechargePwdDialog.setmSetRechargeInterf(setRechargeInterf);
        setRechargePwdDialog.show();
    }


    /**
     * 发起支付
     */
    public void toPay(final Context context, String orderTitle,
                      String orderPrice, String tradePrice, String goodsId, final String tradeApiId) {
        String payMethod = "";
        if (TextUtils.equals("1", tradeApiId)) {
            payMethod = "aliTrade";
        } else if (TextUtils.equals("2", tradeApiId)) {
            payMethod = "wxTrade";
        }
        if (TextUtils.isEmpty(payMethod)) {
            return;
        }
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).toPay("v1", payMethod,
                orderTitle, orderPrice, tradePrice,
                goodsId, tradeApiId, "2"))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            String data = mApiBean.getData();
                            PayMetadata payMetadata = JSON.parseObject(data, PayMetadata.class);
                            if (TextUtils.equals("1", tradeApiId)) {
                                ToALiPay.getInstance().action(context, payMetadata);
                            } else if (TextUtils.equals("2", tradeApiId)) {
                                WeChatPay.getInstance().toWeChatPay(context, payMetadata);
                            }
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {
                    }

                    @Override
                    public void onFinish() {
                    }
                });
    }


    public interface CheckRechargePwdInterf {
        void returnCheckResult(boolean isSuccess);

        void openBindPhone();
    }

    public interface SetRechargeInterf {
        void returnSetPwdResult();
    }
}
