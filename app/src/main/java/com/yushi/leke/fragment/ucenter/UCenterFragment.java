package com.yushi.leke.fragment.ucenter;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.browser.BrowserBaseFragment;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.fragment.setting.SettingFragment;
import com.yushi.leke.fragment.ucenter.mineSubscription.MySubscriptionFragment;
import com.yushi.leke.fragment.ucenter.personalInfo.PersonalInfoFragment;
import com.yushi.leke.fragment.wallet.MyWalletFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(UCenterVu.class)
public class UCenterFragment extends BaseFragment<UCenterContract.IView> implements UCenterContract.Presenter {
    private MyProfileInfo myProfileInfo;//不可编辑
    private MyBaseInfo myBaseInfo;//可编辑

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hasUnreadmsg();
        getMyProfile();
        getMyBaseInfo();
    }

    /**
     * 获取用户信息（不可编辑）
     */
    private void getMyProfile() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getMyProfile())
                .useCache(true)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            myProfileInfo = JSON.parseObject(mApiBean.getData(), MyProfileInfo.class);
                            getVu().updateMyInfo(myProfileInfo, myBaseInfo);
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {
                        getVu().refreshComplete();
                    }
                });
    }


    /**
     * /获取用户信息（可编辑）
     */
    private void getMyBaseInfo() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getMyBaseInfo())
                .useCache(true)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            myBaseInfo = JSON.parseObject(mApiBean.getData(), MyBaseInfo.class);
                            getVu().updateMyInfo(myProfileInfo, myBaseInfo);
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
                                String hasUnreadNoticeMsg = jsonObject.optString("hasUnreadNoticeMsg", "N");
                                String hasUnreadSysMsg = jsonObject.optString("hasUnreadSysMsg", "N");
                                if (TextUtils.equals("Y", hasUnreadNoticeMsg)
                                        || TextUtils.equals("y", hasUnreadNoticeMsg)
                                        || TextUtils.equals("Y", hasUnreadSysMsg)
                                        || TextUtils.equals("y", hasUnreadSysMsg)) {
                                    getVu().hasUnreadMsg(true);
                                    setMainTabMsgState(true);
                                } else {
                                    getVu().hasUnreadMsg(false);
                                    setMainTabMsgState(false);
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
        String avatar = "";
        String userName = "";
        String gender = "";
        String company = "";
        String position = "";
        String motto = "";
        String email = "";
        String city = "";
        String address = "";
        String uid = "";

        if (myBaseInfo != null) {
            avatar = myBaseInfo.getAvatar();
            uid = myBaseInfo.getUid();
            userName = myBaseInfo.getUserName();
            gender = myBaseInfo.getGender();
            company = myBaseInfo.getCompany();
            position = myBaseInfo.getPosition();
            motto = myBaseInfo.getMotto();
            email = myBaseInfo.getEmail();
            city = myBaseInfo.getCity();
            address = myBaseInfo.getAddress();
        }
        getRootFragment().startForResult(UIHelper.creat(PersonalInfoFragment.class)
                .put("avatar", avatar)
                .put("uid", uid)
                .put("userName", userName)
                .put("gender", gender)
                .put("company", company)
                .put("position", position)
                .put("motto", motto)
                .put("email", email)
                .put("city", city)
                .put("address", address)
                .build(), 1000);
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
            getRootFragment().start(UIHelper.creat(MySubscriptionFragment.class).build());
        } else if (TextUtils.equals("我的投票", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyVote()).build());
        } else if (TextUtils.equals("我的等级", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMyGrades()).build());
        } else if (TextUtils.equals("我的会员", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class)
                    .put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMineVip())
                    .put(Global.BUNDLE_KEY_BROWSER_HAVE_HEAD, false)
                    .build());
        } else if (TextUtils.equals("分享好友", key)) {
            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getFriendShare()).build());
        }
    }

    @Override
    public void toRefresh() {
        hasUnreadmsg();
        getMyProfile();
        getMyBaseInfo();
    }

    @Override
    public void openMessagePage() {
        getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getMessage(1)).build());
        getVu().hasUnreadMsg(false);
        setMainTabMsgState(false);
    }

    private void setMainTabMsgState(boolean hasMsg) {
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof MainFragment) {
            ((MainFragment) fragment).hasUnreadMsg(hasMsg);
        }
    }

    public void updatePersonInfo(boolean isAll) {
        if (isAll) {
            hasUnreadmsg();
            getMyProfile();
            getMyBaseInfo();
        } else {
            getMyBaseInfo();
        }
    }
}
