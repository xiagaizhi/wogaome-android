package com.yushi.leke.fragment.login.loginPhone;

import android.animation.LayoutTransition;
import android.graphics.PointF;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.ValueAnimator;
import com.yufan.library.util.CheckUtil;
import com.yufan.library.view.ResizeLayout;
import com.yufan.library.widget.LoginLineView;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_loginphone)
@Title("手机登录")
public class LoginPhoneVu extends BaseVu<LoginPhoneContract.Presenter> implements LoginPhoneContract.IView, View.OnClickListener  {

    @FindView(R.id.et_phone)
    EditText et_phone;
    @FindView(R.id.et_password)
    EditText et_password;
    @FindView(R.id.cb_showeye)
    CheckBox cb_showeye;
    @FindView(R.id.tv_forgetPassword)
    TextView tv_forgetPassword;
    @FindView(R.id.tv_register)
    TextView tv_register;
    @FindView(R.id.iv_clear_password)
    ImageView iv_clear_password;
    @FindView(R.id.iv_clear_phone)
    ImageView iv_clear_phone;
    @FindView(R.id.line_view1)
    LoginLineView line_view1;
    @FindView(R.id.line_view2)
    LoginLineView line_view2;
    @FindView(R.id.bt_login)
    Button bt_login;
    private void updateState(){
        if(CheckUtil.checkInputState(et_phone,et_password,null,false)){
            bt_login.setEnabled(true);
        }else {
            bt_login.setEnabled(false);
        }
    }
    @Override
    public void initView(View view) {
        tv_forgetPassword.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        iv_clear_password.setOnClickListener(this);
        iv_clear_phone.setOnClickListener(this);
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
                if(TextUtils.isEmpty(s)){
                    iv_clear_password.setVisibility(View.GONE);
                }else {
                    iv_clear_password.setVisibility(View.VISIBLE);
                }
                updateState();
            }
        });
        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!TextUtils.isEmpty( et_password.getText())){
                        //显示
                        iv_clear_password.setVisibility(View.VISIBLE);
                    }
                    line_view2.startAnim();
                }else {
                    //隐藏
                    if(TextUtils.isEmpty( et_password.getText())){
                        line_view2.hintAnim();
                    }
                    iv_clear_password.setVisibility(View.GONE);
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
                if(TextUtils.isEmpty(s)){
                    iv_clear_phone.setVisibility(View.GONE);
                }else {

                    iv_clear_phone.setVisibility(View.VISIBLE);
                }
                updateState();
            }
        });
        et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!TextUtils.isEmpty( et_phone.getText())){
                        //显示
                        iv_clear_phone.setVisibility(View.VISIBLE);
                    }
                    line_view1.startAnim();
                }else {
                    //隐藏
                    if(TextUtils.isEmpty( et_phone.getText())){
                        line_view1.hintAnim();
                    }
                    iv_clear_phone.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        ImageView imageView= appToolbar.creatLeftView(ImageView.class);
        imageView.setImageResource(com.yufan.library.R.drawable.left_back_black_arrows);
        imageView.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_forgetPassword:
                mPersenter.onForgetPassword();
                break;
            case R.id.tv_register:
                mPersenter.onRegister();
                break;
            case R.id.iv_clear_password:
                et_password.setText("");
                mPersenter.onClearPassword();
                break;
            case R.id.iv_clear_phone:
                et_phone.setText("");
                mPersenter.onClearPhone();
                break;
            case R.id.bt_login:
                if(CheckUtil.checkInputState(et_phone,et_password,null,true)){
                    mPersenter.login(et_phone.getText().toString(),et_password.getText().toString());
                }

                break;
            default:
                break;
        }
    }

}
