package com.yushi.leke.fragment.ucenter;

import android.os.Bundle;

import com.yufan.library.Global;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.browser.BrowserBaseFragment;
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

    @Override
    public void openBrowserPage(String key) {
        if (TextUtils.equals("我的路演", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://baidu.com").build());
        } else if (TextUtils.equals("我的订阅", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://baidu.com").build());
        } else if (TextUtils.equals("我的投票", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://baidu.com").build());
        } else if (TextUtils.equals("我的等级", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://web.leke-dev.com/#/myGrade").build());
        } else if (TextUtils.equals("我的会员", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://web.leke-dev.com/#/myLeaguer").build());
        } else if (TextUtils.equals("分享好友", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://baidu.com").build());
        }
    }
}
