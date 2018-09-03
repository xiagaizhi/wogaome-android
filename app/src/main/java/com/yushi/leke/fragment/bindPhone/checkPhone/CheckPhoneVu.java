package com.yushi.leke.fragment.bindPhone.checkPhone;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yufan.library.util.CheckUtil;
import com.yufan.library.widget.LoginLineView;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.util.StringUtil;
import com.yushi.leke.widget.VerificationCodeTextView;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_checkphone)
@Title("安全校验")
public class CheckPhoneVu extends BaseVu<CheckPhoneContract.Presenter> implements CheckPhoneContract.IView {
    @FindView(R.id.tv_phone)
    TextView tv_phone;
    @FindView(R.id.et_verification_code)
    EditText et_verification_code;
    @FindView(R.id.tv_vcode)
    VerificationCodeTextView verificationCodeTextView;
    @FindView(R.id.bt_submit)
    Button bt_submit;
    @FindView(R.id.line_view2)
    LoginLineView line_view2;

    public void initView(View view) {
        bt_submit.setOnClickListener(this);
        verificationCodeTextView.setOnGetCodeClickListener(new VerificationCodeTextView.OnGetCodeClickListener() {
            @Override
            public boolean getCode(String sessionId) {
                if (mPersenter.getVerifcationCode()) {
                    return true;
                }
                return false;
            }
        });
        et_verification_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    line_view2.startAnim();
                } else {//隐藏
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
        if (CheckUtil.checkInputState(null, null, et_verification_code, false)) {
            bt_submit.setEnabled(true);
        } else {
            bt_submit.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (CheckUtil.checkInputState(null, null, et_verification_code, true)) {
                    mPersenter.checkPhone(et_verification_code.getText().toString());
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

    @Override
    public void returnPhoneNumber(String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            tv_phone.setText(StringUtil.handlePhoneNumber(phoneNumber));
        }
    }
}
