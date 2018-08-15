package com.yushi.leke.plugin.fragment.resetpassword;

import android.os.Bundle;

import com.yushi.leke.plugin.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;

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
}
