package com.yushi.leke.fragment.bindPhone.updatePhone;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.yufan.library.util.CheckUtil;
import com.yufan.library.widget.LoginLineView;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.widget.VerificationCodeTextView;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_updatephone)
@Title("设置新手机号")
public class UpdatePhoneVu extends BaseVu<UpdatePhoneContract.Presenter> implements UpdatePhoneContract.IView {
    @FindView(R.id.et_phone)
    EditText et_phone;
    @FindView(R.id.iv_clear_phone)
    ImageView iv_clear_phone;
    @FindView(R.id.et_verification_code)
    EditText et_verification_code;
    @FindView(R.id.tv_vcode)
    VerificationCodeTextView verificationCodeTextView;
    @FindView(R.id.bt_submit)
    Button bt_submit;
    @FindView(R.id.line_view1)
    LoginLineView line_view1;
    @FindView(R.id.line_view2)
    LoginLineView line_view2;


    public void initView(View view) {
        iv_clear_phone.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        verificationCodeTextView.setOnGetCodeClickListener(new VerificationCodeTextView.OnGetCodeClickListener() {
            @Override
            public void getCode(String sessionId) {
                mPersenter.getVerifcationCode(sessionId,et_phone.getText().toString());

            }

            @Override
            public String getPhone() {
                return et_phone.getText().toString();
            }


        });

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    iv_clear_phone.setVisibility(View.GONE);
                } else {
                    iv_clear_phone.setVisibility(View.VISIBLE);
                }
                updateState();
            }
        });
        et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(et_phone.getText())) {
                        //显示
                        iv_clear_phone.setVisibility(View.VISIBLE);
                    }
                    line_view1.startAnim();
                } else {
                    //隐藏
                    iv_clear_phone.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(et_phone.getText())) {
                        line_view1.hintAnim();
                    }
                }
            }
        });
        et_verification_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line_view2.startAnim();
                } else {
                    //隐藏
                    if (TextUtils.isEmpty(et_verification_code.getText())) {
                        line_view2.hintAnim();
                    }
                }
            }
        });
        et_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateState();
            }
        });
    }

    private void updateState() {
        if (CheckUtil.checkInputEmpty(et_phone, null, et_verification_code, false)) {
            bt_submit.setEnabled(true);
        } else {
            bt_submit.setEnabled(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (CheckUtil.checkInputState(et_phone, null, et_verification_code, true)) {
                    mPersenter.updatePhone(et_phone.getText().toString(), et_verification_code.getText().toString());
                }
                break;
            case R.id.iv_clear_phone:
                et_phone.setText("");
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
