package com.yufan.library.pay;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.yufan.library.R;
import com.yufan.library.widget.customkeyboard.KeyboardAdapter;
import com.yufan.library.widget.customkeyboard.KeyboardView;
import com.yufan.library.widget.customkeyboard.PayPsdInputView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanyangyang on 18/8/24.
 */

public class SetRechargePwdDialog extends Dialog implements KeyboardAdapter.OnKeyboardClickListener {
    private PayPsdInputView tv_password;
    private KeyboardView id_keyboard_view;
    private TextView mTitle;
    private Context mContext;
    private boolean isSetRecharge;//true:设置／修改交易密码 false:输入支付密码进行验证


    public SetRechargePwdDialog(@NonNull Context context, final boolean isSetRecharge) {
        super(context, R.style.dialog_common);
        this.mContext = context;
        this.isSetRecharge = isSetRecharge;
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_setrechargepassword, null);
        setContentView(rootView);
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
                //两次输入密码相同，那就去进行支付楼
                tv_password.setComparePassword("");
                tv_password.cleanPsd();
                Toast.makeText(mContext, "交易密码设置成功", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void inputFinished(String inputPsd) {
                //输完逻辑
                if (isSetRecharge) {//设置交易
                    tv_password.setComparePassword(inputPsd);
                    tv_password.cleanPsd();
                    mTitle.setText(R.string.set_recharge_pwd_again);
                } else {//输入支付密码进行验证
                    tv_password.setComparePassword("");
                    if (TextUtils.equals("123456", inputPsd)) {//成功
                        Toast.makeText(mContext, "去支付", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {//失败
                        Toast.makeText(mContext, "密码有误，请重新输入", Toast.LENGTH_SHORT).show();
                        tv_password.cleanPsd();
                    }
                }
            }
        });
    }

    public void setmTitle(int resId) {
        mTitle.setText(resId);
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
}
