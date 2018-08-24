package com.yushi.leke.fragment.login.loginPhone;

import android.content.Intent;
import android.os.Bundle;

import com.yufan.library.Global;
import com.yufan.library.browser.BrowserBaseFragment;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.SoftInputUtil;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.register.RegisterFragment;
import com.yushi.leke.fragment.resetPassword.ResetPasswordFragment;
import com.yushi.leke.uamp.ui.FullScreenPlayerActivity;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(LoginPhoneVu.class)
public class LoginPhoneFragment extends BaseFragment<LoginPhoneContract.IView> implements LoginPhoneContract.Presenter {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onRefresh() {

    }



    @Override
    public void onRegister() {
        start(UIHelper.creat(RegisterFragment.class).build());
    }

    @Override
    public void onForgetPassword() {

        start(UIHelper.creat(ResetPasswordFragment.class).build());

    }

    @Override
    public void onClearPhone() {

    }

    @Override
    public void onClearPassword() {

    }

    @Override
    public void login(String phone, String password) {

    }

    @Override
    public void onAgreementClick() {
        start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL,"http://baidu.com").build());
    }

    @Override
    public void onDestroyView() {
        SoftInputUtil.hideSoftInput(getActivity(),getView());
        super.onDestroyView();
    }
}
