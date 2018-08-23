package com.yushi.leke.fragment.login;

import android.animation.LayoutTransition;
import android.graphics.PointF;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.animation.ValueAnimator;
import com.yufan.library.inject.FindView;
import com.yufan.library.view.ResizeLayout;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_login)
@Title("登录")
public class LoginVu extends BaseVu<LoginContract.Presenter> implements LoginContract.IView, View.OnClickListener {

    @FindView(R.id.tv_agreement)
    TextView tv_agreement;
    @FindView(R.id.tv_register)
    TextView tv_register;
    @FindView(R.id.rl_login_weixin)
    RelativeLayout rl_login_weixin;
    @FindView(R.id.rl_login_phone)
    RelativeLayout rl_login_phone;
    @FindView(R.id.iv_logo)
    ImageView iv_logo;

    @Override
    public void initView(View view) {
        tv_agreement.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        rl_login_weixin.setOnClickListener(this);
        rl_login_phone.setOnClickListener(this);
        iv_logo.setOnClickListener(this);
    }


    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_agreement:
                mPersenter.onAgreementClick();
                break;
            case R.id.tv_register:
            mPersenter.onRegisterClick();
                break;
            case R.id.rl_login_weixin:
            mPersenter.onWeixinLoginClick();
                break;
            case R.id.rl_login_phone:
            mPersenter.onPhoneLoginClick();
                break;
            case R.id.iv_logo:
                mPersenter.onLogoClick();
                break;
                default:
                    break;
        }
    }


}
