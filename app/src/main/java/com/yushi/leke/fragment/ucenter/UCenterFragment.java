package com.yushi.leke.fragment.ucenter;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.setting.SettingFragment;
import com.yushi.leke.fragment.ucenter.personalInfo.PersonalInfoFragment;
import com.yushi.leke.fragment.wallet.MyWalletFragment;
/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(UCenterVu.class)
public class UCenterFragment extends BaseFragment<UCenterContract.IView> implements UCenterContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void startPlayer() {

    }

    @Override
    public void openMyWallet() {
        getRootFragment().start(UIHelper.creat(MyWalletFragment.class).build());
    }

    @Override
    public void openPersonalpage() {
        getRootFragment().start(UIHelper.creat(PersonalInfoFragment.class).build());
    }

    @Override
    public void share() {
    }

    @Override
    public void openSettingPage() {
        getRootFragment().start(UIHelper.creat(SettingFragment.class).build());
    }
}
