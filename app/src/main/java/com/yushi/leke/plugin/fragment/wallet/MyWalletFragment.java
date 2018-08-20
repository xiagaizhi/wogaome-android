package com.yushi.leke.plugin.fragment.wallet;

import android.os.Bundle;

import com.yushi.leke.plugin.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.plugin.UIHelper;
import com.yushi.leke.plugin.fragment.setrechargepassword.SetRechargePasswordFragment;

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
    public void setRechargePwd() {
        start(UIHelper.creat(SetRechargePasswordFragment.class).build());
    }
}
