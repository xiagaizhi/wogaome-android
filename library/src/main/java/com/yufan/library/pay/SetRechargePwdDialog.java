package com.yufan.library.pay;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yufan.library.R;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.remote.BaseApi;
import com.yufan.library.pay.alipay.ToALiPay;
import com.yufan.library.pay.wenchatpay.WeChatPay;
import com.yufan.library.util.ToastUtil;
import com.yufan.library.widget.customkeyboard.KeyboardAdapter;
import com.yufan.library.widget.customkeyboard.KeyboardView;
import com.yufan.library.widget.customkeyboard.PayPsdInputView;

import java.lang.reflect.Method;

/**
 * Created by zhanyangyang on 18/8/24.
 */

public class SetRechargePwdDialog extends Dialog implements KeyboardAdapter.OnKeyboardClickListener {
    public static final int SET_RECHARGE_PWD = 1;//设置交易密码
    public static final int MODIFY_RECHARGE_PWD = 2;//修改交易密码
    public static final int CHECK_RECHARGE_PWD = 3;//输入支付密码进行校验
    private PayPsdInputView tv_password;
    private KeyboardView id_keyboard_view;
    private TextView mTitle;
    private Context mContext;
    private int type;//true:设置／修改交易密码 false:输入支付密码进行验证
    private TextView mSetRechargeType;
    private String payApiId;
    private String orderTitle;
    private String orderPrice;
    private String payPrice;
    private String goodsId;

    public SetRechargePwdDialog(@NonNull Context context, final int type, String payApiId, String orderTitle, String orderPrice, String payPrice, String goodsId) {
        super(context, R.style.dialog_common);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_setrechargepassword, null);
        setContentView(rootView);
        this.mContext = context;
        this.type = type;
        this.payApiId = payApiId;
        this.orderTitle = orderTitle;
        this.orderPrice = orderPrice;
        this.payPrice = payPrice;
        this.goodsId = goodsId;
        initView(rootView);
    }

    public SetRechargePwdDialog(@NonNull Context context, final int type) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_setrechargepassword, null);
        setContentView(rootView);
        this.mContext = context;
        this.type = type;
        initView(rootView);
    }

    private void initView(View rootView) {
        mSetRechargeType = rootView.findViewById(R.id.tv_recharge_type);
        if (type == SET_RECHARGE_PWD) {
            mSetRechargeType.setText("设置交易密码");
        } else if (type == MODIFY_RECHARGE_PWD) {
            mSetRechargeType.setText("修改交易密码");
        } else if (type == CHECK_RECHARGE_PWD) {
            mSetRechargeType.setText("验证交易密码");
        }
        mTitle = rootView.findViewById(R.id.tv_setpwd_title);
        rootView.findViewById(R.id.id_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        rootView.findViewById(R.id.id_top_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_password = rootView.findViewById(R.id.tv_password);
        id_keyboard_view = rootView.findViewById(R.id.id_keyboard_view);
        setCanceledOnTouchOutside(false);
        //设置不调用系统键盘
        if (Build.VERSION.SDK_INT <= 10) {
            tv_password.setInputType(InputType.TYPE_NULL);
        } else {
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(tv_password, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!id_keyboard_view.isVisible()) {
                    id_keyboard_view.show();
                }
            }
        });
        id_keyboard_view.setOnKeyBoardClickListener(this);
        tv_password.setComparePassword(new PayPsdInputView.onPasswordListener() {
            @Override
            public void onDifference(String oldPsd, String newPsd) {
                //和上次输入的密码不一致  做相应的业务逻辑处理
                tv_password.setComparePassword("");
                tv_password.cleanPsd();
                Toast.makeText(mContext, "两次交易密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                mTitle.setText(R.string.set_recharge_pwd);

            }

            @Override
            public void onEqual(String psd) {
                //两次输入密码相同，去设置密码
                setRechargePwd(psd);
                tv_password.setComparePassword("");
                tv_password.cleanPsd();
            }

            @Override
            public void inputFinished(String inputPsd) {
                //输完逻辑
                if (type == SET_RECHARGE_PWD || type == MODIFY_RECHARGE_PWD) {//设置交易、修改交易密码
                    tv_password.setComparePassword(inputPsd);
                    tv_password.cleanPsd();
                    mTitle.setText(R.string.set_recharge_pwd_again);
                } else if (type == CHECK_RECHARGE_PWD) {//输入支付密码进行验证
                    checkPayPwd(inputPsd);
                    tv_password.setComparePassword("");
                    tv_password.cleanPsd();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        window.setAttributes(p);
        window.setDimAmount(0.6f);
        window.setWindowAnimations(R.style.AnimBottomDialog);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void onKeyClick(String data) {
        tv_password.setText(tv_password.getText().toString().trim() + data);
        tv_password.setSelection(tv_password.getText().length());
    }

    @Override
    public void onDeleteClick() {
        // 点击删除按钮
        String num = tv_password.getText().toString().trim();
        if (num.length() > 0) {
            tv_password.setText(num.substring(0, num.length() - 1));
            tv_password.setSelection(tv_password.getText().length());
        }
    }

    private void setRechargePwd(String pwd) {
        ApiManager.getCall(ApiManager.getInstance().create(BaseApi.class).setPayPwd("v1", "999", pwd))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            ToastUtil.normal(mContext, "交易密码设置成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            ToastUtil.normal(mContext, "密码设置失败，请重新设置", Toast.LENGTH_SHORT).show();
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
     * 验证交易密码
     */
    private void checkPayPwd(String pwd) {
        ApiManager.getCall(ApiManager.getInstance().create(BaseApi.class).verifyPayPwd("v1", "999", pwd))
                .useCache(false).
                enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            toPay();
                            dismiss();
                        } else {
                            ToastUtil.normal(mContext, "密码有误，请重新输入", Toast.LENGTH_SHORT).show();
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
     * 密码验证通过去支付
     */
    private void toPay() {
        String payMethod = "";
        if (TextUtils.equals("1", payApiId)) {
            payMethod = "aliPay";
        } else if (TextUtils.equals("2", payApiId)) {
            payMethod = "wxPay";
        }
        if (TextUtils.isEmpty(payMethod)) {
            return;
        }
        ApiManager.getCall(ApiManager.getInstance().create(BaseApi.class).toPay("v1", payMethod,
                orderTitle, orderPrice, payPrice,
                goodsId, payApiId, "2"))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            String data = mApiBean.getData();
                            PayMetadata payMetadata = JSON.parseObject(data, PayMetadata.class);
                            if (TextUtils.equals("1", payApiId)) {
                                ToALiPay.getInstance().action(mContext, payMetadata);
                            } else if (TextUtils.equals("2", payApiId)) {
                                WeChatPay.getInstance().toWeChatPay(mContext, payMetadata);
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
}
