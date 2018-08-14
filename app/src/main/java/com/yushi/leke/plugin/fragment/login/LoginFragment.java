package com.yushi.leke.plugin.fragment.login;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.DialogManager;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(LoginVu.class)
public class LoginFragment extends BaseFragment<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onKeyboardShow(int keyHeight) {
        DialogManager.getInstance().toast(keyHeight+"");
    }

    @Override
    public void onKeyboardHide() {
        DialogManager.getInstance().toast("onKeyboardHide");
    }

    @Override
    public void onKeyboardShowOver() {

    }

    @Override
    public void onRegister() {

    }

    @Override
    public void onForgetPassword() {

    }

    @Override
    public void onLogin(String phone, String password) {

    }
}
