package com.yushi.leke.fragment.setRechargePassword;

import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.yufan.library.widget.customkeyboard.KeyboardAdapter;
import com.yufan.library.widget.customkeyboard.KeyboardView;
import com.yufan.library.widget.customkeyboard.PayPsdInputView;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

import java.lang.reflect.Method;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_setrechargepassword)
@Title("设置交易密码")
public class SetRechargePasswordVu extends BaseVu<SetRechargePasswordContract.Presenter> implements SetRechargePasswordContract.IView, View.OnClickListener, KeyboardAdapter.OnKeyboardClickListener {
    @FindView(R.id.id_password)
    PayPsdInputView id_password;
    @FindView(R.id.id_keyboard_view)
    KeyboardView id_keyboard_view;

    @Override
    public void initView(View view) {
        //设置不调用系统键盘
        if (Build.VERSION.SDK_INT <= 10) {
            id_password.setInputType(InputType.TYPE_NULL);
        } else {
            mPersenter.hideSoftInput();
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(id_password, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        id_password.setOnClickListener(this);
        id_keyboard_view.setOnKeyBoardClickListener(this);
        id_password.setComparePassword(new PayPsdInputView.onPasswordListener() {
            @Override
            public void onDifference(String oldPsd, String newPsd) {
                // TODO: 2018/1/22  和上次输入的密码不一致  做相应的业务逻辑处理
                id_password.setComparePassword("");
                id_password.cleanPsd();
            }

            @Override
            public void onEqual(String psd) {
                // TODO: 2017/5/7 两次输入密码相同，那就去进行支付楼
                id_password.setComparePassword("");
                id_password.cleanPsd();
            }

            @Override
            public void inputFinished(String inputPsd) {
                // TODO: 2018/1/3 输完逻辑
                id_password.setComparePassword(inputPsd);
                id_password.cleanPsd();
            }
        });
    }

    @Override
    public void onKeyClick(String data) {
        id_password.setText(id_password.getText().toString().trim() + data);
        id_password.setSelection(id_password.getText().length());
    }

    @Override
    public void onDeleteClick() {
        // 点击删除按钮
        String num = id_password.getText().toString().trim();
        if (num.length() > 0) {
            id_password.setText(num.substring(0, num.length() - 1));
            id_password.setSelection(id_password.getText().length());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_password:
                if (!id_keyboard_view.isVisible()) {
                    id_keyboard_view.show();
                }
                break;
        }
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return super.initTitle(appToolbar);
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
