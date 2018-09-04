package com.yushi.leke.fragment.ucenter;

import android.os.Bundle;

import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.browser.BrowserBaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.fragment.setting.SettingFragment;
import com.yushi.leke.fragment.ucenter.personalInfo.PersonalInfoFragment;
import com.yushi.leke.fragment.wallet.MyWalletFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(UCenterVu.class)
public class UCenterFragment extends BaseFragment<UCenterContract.IView> implements UCenterContract.Presenter {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hasUnreadmsg();
    }

    /**
     * 是否有未读消息
     */
    private void hasUnreadmsg() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).hasUnreadMsg())
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        String data = mApiBean.getData();
                        if (!TextUtils.isEmpty(data)) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                String hasUnreadMsg = jsonObject.optString("hasUnreadMsg", "N");
                                if (TextUtils.equals("Y", hasUnreadMsg) || TextUtils.equals("y", hasUnreadMsg)) {
                                    getVu().hasUnreadMsg(true);
                                    Fragment fragment = getParentFragment();
                                    if (fragment != null && fragment instanceof MainFragment) {
                                        ((MainFragment) fragment).hasUnreadMsg(true);
                                    }
                                } else {
                                    getVu().hasUnreadMsg(false);
                                    Fragment fragment = getParentFragment();
                                    if (fragment != null && fragment instanceof MainFragment) {
                                        ((MainFragment) fragment).hasUnreadMsg(false);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
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
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyRoadShow()).build());
        } else if (TextUtils.equals("我的订阅", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMySubscribe()).build());
        } else if (TextUtils.equals("我的投票", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyVote()).build());
        } else if (TextUtils.equals("我的等级", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyGrades()).build());
        } else if (TextUtils.equals("我的会员", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMineVip()).build());
        } else if (TextUtils.equals("分享好友", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getFriendShare()).build());
        }
    }
}
