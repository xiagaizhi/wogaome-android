package com.yushi.leke.fragment.wallet;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yufan.library.pay.PayDialog;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.bindPhone.BindPhoneFragment;
import com.yushi.leke.fragment.openTreasureBox.OpenTreasureBoxFragment;
import com.yushi.leke.fragment.paySafe.PaySafetyFragment;
import com.yushi.leke.fragment.resetPassword.ResetPasswordFragment;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(MyWalletVu.class)
public class MyWalletFragment extends BaseFragment<MyWalletContract.IView> implements MyWalletContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void openPaySafety() {
        getRootFragment().start(UIHelper.creat(ResetPasswordFragment.class).build());
    }

    @Override
    public void openPlayer() {
        getRootFragment().start(UIHelper.creat(BindPhoneFragment.class).build());
    }

    @Override
    public void openTreasureBox() {
        getRootFragment().start(UIHelper.creat(OpenTreasureBoxFragment.class).build());
    }
}
