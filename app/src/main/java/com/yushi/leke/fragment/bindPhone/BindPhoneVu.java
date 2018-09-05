package com.yushi.leke.fragment.bindPhone;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.inject.AnnotateUtils;
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

import java.util.Map;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_bindphone)
@Title("绑定手机")
public class BindPhoneVu extends BaseVu<BindPhoneContract.Presenter> implements BindPhoneContract.IView {
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

    @FindView(R.id.et_password)
    EditText et_password;
    @FindView(R.id.cb_showeye)
    CheckBox cb_showeye;
    @FindView(R.id.iv_clear_password)
    ImageView iv_clear_password;
    @FindView(R.id.line_view3)
    LoginLineView line_view3;

    TextView titleName;

    public void initView(View view) {
        iv_clear_phone.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
        verificationCodeTextView.setOnGetCodeClickListener(new VerificationCodeTextView.OnGetCodeClickListener() {
            @Override
            public void getCode(String sessionId) {
                mPersenter.getVerifcationCode(et_phone.getText().toString());
            }
            @Override
            public boolean canShow() {
                return !TextUtils.isEmpty(et_phone.getText());
            }
        });

        cb_showeye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    et_password.requestFocus();
                } else {
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    et_password.requestFocus();

                }
            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    iv_clear_password.setVisibility(View.GONE);
                } else {
                    updateState();
                    iv_clear_password.setVisibility(View.VISIBLE);
                }
            }
        });
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!TextUtils.isEmpty(et_password.getText())) {
                        //显示
                        iv_clear_password.setVisibility(View.VISIBLE);
                    }
                    line_view3.startAnim();
                } else {
                    //隐藏
                    iv_clear_password.setVisibility(View.GONE);
                    if (TextUtils.isEmpty(et_password.getText())) {
                        line_view3.hintAnim();
                    }
                }
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
        if (CheckUtil.checkInputEmpty(et_phone, et_password, et_verification_code, false)) {
            bt_submit.setEnabled(true);
        } else {
            bt_submit.setEnabled(false);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                if (CheckUtil.checkInputState(et_phone, et_password, et_verification_code, true)) {
                    mPersenter.bindPhone(et_phone.getText().toString(), et_verification_code.getText().toString(), et_password.getText().toString());
                }
                break;
            case R.id.iv_clear_phone:
                et_phone.setText("");
                break;
        }
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        String titleNameStr = AnnotateUtils.getTitle(this);
        if (!TextUtils.isEmpty(titleNameStr)) {
            titleName = appToolbar.creatCenterView(TextView.class);
            titleName.setText(titleNameStr);
        }
        ImageView leftView = appToolbar.creatLeftView(ImageView.class);
        leftView.setImageResource(com.yufan.library.R.drawable.left_back_black_arrows);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.onBackPressed();
            }
        });
        appToolbar.build();
        return true;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
