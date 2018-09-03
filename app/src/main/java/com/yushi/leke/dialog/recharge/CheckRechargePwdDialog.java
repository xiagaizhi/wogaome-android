package com.yushi.leke.dialog.recharge;

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

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.widget.customkeyboard.KeyboardAdapter;
import com.yufan.library.widget.customkeyboard.KeyboardView;
import com.yufan.library.widget.customkeyboard.PayPsdInputView;
import com.yushi.leke.R;
import com.yushi.leke.YFApi;
import com.yushi.leke.util.RechargeUtil;

import java.lang.reflect.Method;

/**
 * Created by zhanyangyang on 18/8/24.
 */

public class CheckRechargePwdDialog extends Dialog implements KeyboardAdapter.OnKeyboardClickListener {
    private PayPsdInputView tv_password;
    private KeyboardView id_keyboard_view;
    private TextView mTitle;
    private Context mContext;
    private TextView mSetRechargeType;
    private RechargeUtil.CheckRechargePwdInterf mCheckRechargePwd;
    private String mExtMsg;

    public CheckRechargePwdDialog(@NonNull Context context, String extMsg, RechargeUtil.CheckRechargePwdInterf checkRechargePwd) {
        super(context);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_setrechargepassword, null);
        setContentView(rootView);
        this.mContext = context;
        this.mExtMsg = extMsg;
        this.mCheckRechargePwd = checkRechargePwd;
        initView(rootView);
    }

    private void initView(View rootView) {
        mSetRechargeType = rootView.findViewById(R.id.tv_recharge_type);
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
        mTitle.setText("请输入交易密码以验证身份");
//        mSetRechargeType.setText("验证交易密码");
//        mSetRechargeType.setText("修改交易密码");
        mSetRechargeType.setText(mExtMsg);

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
            }

            @Override
            public void onEqual(String psd) {
                //两次输入密码相同，去设置密码
            }

            @Override
            public void inputFinished(String inputPsd) {
                //输完逻辑
                checkPayPwd(inputPsd);
                tv_password.setComparePassword("");
                tv_password.cleanPsd();
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

    /**
     * 验证交易密码
     */
    private void checkPayPwd(String pwd) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).verifyTradePwd(pwd))
                .useCache(false).
                enqueue(new BaseHttpCallBack() {

                    @Override
                    public void onResponse(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            if (mCheckRechargePwd != null) {
                                mCheckRechargePwd.returnCheckResult(true);
                            }
                            dismiss();
                        } else {
                            DialogManager.getInstance().toast("密码有误，请重新输入");
                            mCheckRechargePwd.returnCheckResult(false);
                        }
                    }

                    @Override
                    public void onFailure(int id, Exception e) {
                        super.onFailure(id, e);
                        mCheckRechargePwd.returnCheckResult(false);
                    }

                    @Override
                    public void onSuccess(ApiBean mApiBean) {

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
