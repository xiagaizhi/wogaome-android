package com.yushi.leke.fragment.login;

import android.animation.LayoutTransition;
import android.graphics.PointF;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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
public class LoginVu extends BaseVu<LoginContract.Presenter> implements LoginContract.View, View.OnClickListener {
    @FindView(R.id.sd_login_bg)
    SimpleDraweeView sd_login_bg;
    @FindView(R.id.resize_login)
    ResizeLayout resize_login;
    @FindView(R.id.tv_login_title)
    TextView tv_login_title;
    @FindView(R.id.ll_content)
    LinearLayout ll_content;
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
    private int titleHeight;
    @Override
    public void initView(View view) {
        tv_forgetPassword.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        PointF pointF = new PointF(1f, 0f);
        sd_login_bg.getHierarchy().setActualImageFocusPoint(pointF);
        sd_login_bg.getHierarchy().setPlaceholderImageFocusPoint(pointF);
        LayoutTransition transition = new LayoutTransition();
        resize_login.setLayoutTransition(transition);
        resize_login.setOnKeyboardShowListener(new ResizeLayout.OnKeyboardChangedListener() {
            @Override
            public void onKeyboardShow(int keyHeight) {
                mPersenter.onKeyboardShow(keyHeight);
                titleHeight= tv_login_title.getHeight();
                final ValueAnimator animator = ValueAnimator.ofInt(titleHeight, 0);
                animator.setDuration(100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) tv_login_title.getLayoutParams();
                        layoutParams.height=value;
                        tv_login_title.setLayoutParams(layoutParams);
                    }
                });
                animator.start();

            }

            @Override
            public void onKeyboardHide() {
                mPersenter.onKeyboardHide();
                final ValueAnimator animator = ValueAnimator.ofInt(0, titleHeight);
                animator.setDuration(100);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) tv_login_title.getLayoutParams();
                        layoutParams.height=value;
                        tv_login_title.setLayoutParams(layoutParams);
                    }
                });
                animator.start();

            }

            @Override
            public void onKeyboardShowOver() {
                mPersenter.onKeyboardShowOver();
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
            case R.id.tv_forgetPassword:
                mPersenter.onForgetPassword();
                break;
            case R.id.tv_register:
            mPersenter.onRegister();
                break;
                default:
                    break;
        }
    }
    private void scaleAnim(final View view,int from, int to) {
        //1.调用ofInt(int...values)方法创建ValueAnimator对象
        ValueAnimator mAnimator = ValueAnimator.ofInt(from, to);
        //2.为目标对象的属性变化设置监听器
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                int animatorValue = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams marginLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                marginLayoutParams.height = animatorValue;
                view.setLayoutParams(marginLayoutParams);
            }
        });
        mAnimator.setDuration(100);
        mAnimator.setTarget(view);
        mAnimator.start();
    }

}
