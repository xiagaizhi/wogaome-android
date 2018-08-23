package com.yushi.leke.fragment.register;

import android.os.Bundle;

import com.alibaba.verificationsdk.ui.IActivityCallback;
import com.alibaba.verificationsdk.ui.VerifyActivity;
import com.alibaba.verificationsdk.ui.VerifyType;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.inject.VuClass;
import com.yufan.library.util.SoftInputUtil;

import java.util.Map;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(RegisterVu.class)
public class RegisterFragment extends BaseFragment<RegisterContract.IView> implements RegisterContract.Presenter {

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
    public void getVerifcationCode() {

    }

    @Override
    public void verify() {
       VerifyActivity.startSimpleVerifyUI(getContext(), VerifyType.TILTBALL, "code", null, new IActivityCallback() {
           @Override
           public void onNotifyBackPressed() {

           }

           @Override
           public void onResult(int i, Map<String, String> map) {

           }
       });
    }

    @Override
    public void register(String phone, String password, String verifcationCode) {

    }

    @Override
    public void onDestroyView() {
        SoftInputUtil.hideSoftInput(getActivity(),getView());
        super.onDestroyView();
    }
}
