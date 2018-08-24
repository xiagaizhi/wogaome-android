package com.yushi.leke.fragment.resetPassword;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yufan.library.util.SoftInputUtil;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(ResetPasswordVu.class)
public class ResetPasswordFragment extends BaseFragment<ResetPasswordContract.IView> implements ResetPasswordContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void getVerifcationCode() {

    }

    @Override
    public void resetPassword(String phone, String vcode, String newPassword) {

    }
    @Override
    public void onDestroyView() {
        SoftInputUtil.hideSoftInput(getActivity(),getView());
        super.onDestroyView();
    }
}
