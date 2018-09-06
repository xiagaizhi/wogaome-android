package com.yushi.leke.util;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.pay.PayMetadata;
import com.yufan.library.pay.alipay.ToALiPay;
import com.yufan.library.pay.wenchatpay.WeChatPay;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;
import com.yushi.leke.dialog.recharge.CheckRechargePwdDialog;
import com.yushi.leke.dialog.recharge.SetRechargePwdDialog;

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


    public void checkRechargePwd(Context context, String extMsg, CheckRechargePwdInterf checkRechargeInterf) {
        CheckRechargePwdDialog checkRechargePwdDialog = new CheckRechargePwdDialog(context, extMsg, checkRechargeInterf);
        checkRechargePwdDialog.show();
    }

    /**
     * @param context
     * @param extMsg
     * @param haveRechargePwdInterf
     */
    public void haveRechargePwd(final Context context, final String extMsg, final HaveRechargePwdInterf haveRechargePwdInterf) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).haveTradePwd())
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
                                        if (haveRechargePwdInterf != null) {
                                            haveRechargePwdInterf.haveRechargePwd(true);
                                        }
                                    } else {//没有交易密码
//                                            setRechargePwd(context, SetRechargePwdDialog.SET_RECHARGE_PWD, null);
                                        new CommonDialog(context).setTitle("您尚未设置交易密码，请前往设置")
                                                .setNegativeName("取消")
                                                .setPositiveName("去设置")
                                                .setHaveNegative(true)
                                                .setCommonDialogCanceledOnTouchOutside2(false)
                                                .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                                                    @Override
                                                    public void onClick(CommonDialog commonDialog, int actionType) {
                                                        commonDialog.dismiss();
                                                        if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {
                                                            // TODO: 2018/9/5  去设置密码
                                                            if (haveRechargePwdInterf != null) {
                                                                haveRechargePwdInterf.setRechargePwd();
                                                            }
                                                        }
                                                    }
                                                }).show();
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
                                                        // TODO: 2018/9/5  去绑定手机
                                                        if (haveRechargePwdInterf != null) {
                                                            haveRechargePwdInterf.bindPhone();
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


    /**
     * 设置交易密码
     *
     * @param context
     * @param type
     * @param setRechargeInterf
     */
    public void setRechargePwd(Context context, String token, String originalPwd, int type, SetRechargeInterf setRechargeInterf) {
        SetRechargePwdDialog setRechargePwdDialog = new SetRechargePwdDialog(context, type, token, originalPwd);
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
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).toPay(payMethod,
                orderTitle, orderPrice, tradePrice,
                goodsId, tradeApiId, "2"))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        String data = mApiBean.getData();
                        if (!TextUtils.isEmpty(data)) {
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
        void returnCheckResult(boolean isSuccess, String originalPwd);
    }

    public interface HaveRechargePwdInterf {
        void haveRechargePwd(boolean haveRechargePwe);

        void bindPhone();

        void setRechargePwd();
    }

    public interface SetRechargeInterf {
        void returnSetPwdResult(boolean isSuccess);
    }
}
