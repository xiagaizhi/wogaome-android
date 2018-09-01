package com.yushi.leke.fragment.ucenter;

import android.content.Intent;
import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yufan.share.ShareModel;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.ucenter.personalInfo.PersonalInfoFragment;
import com.yushi.leke.fragment.wallet.MyWalletFragment;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.share.ShareMenuActivity;

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
        ShareModel shareModel=  new ShareModel();
        shareModel.setContent("内容");
        shareModel.setTargetUrl("http://www.baidu.com");
        shareModel.setTitle("title");
        shareModel.setIcon("https://gitbook.cn/gitchat/author/5a002a147393bc6262dfb1c2");
        ShareMenuActivity.startShare(this,shareModel);
    }
}
