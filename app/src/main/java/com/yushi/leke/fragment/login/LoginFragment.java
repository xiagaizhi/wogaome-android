package com.yushi.leke.fragment.login;

import android.content.Intent;
import android.os.Bundle;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yufan.library.Global;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.browser.BrowserBaseFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.DialogManager;
import com.yufan.share.ILoginCallback;
import com.yufan.share.ShareUtils;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.login.loginPhone.LoginPhoneFragment;
import com.yushi.leke.fragment.register.RegisterFragment;
import com.yushi.leke.uamp.ui.FullScreenPlayerActivity;

import java.util.Map;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(LoginVu.class)
public class LoginFragment extends BaseFragment<LoginContract.IView> implements LoginContract.Presenter {


    private ShareUtils mShareUtils;



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mShareUtils=new ShareUtils(getActivity());
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onBackPressed() {
        pop();
    }


    @Override
    public void onRegisterClick() {
        start(UIHelper.creat(RegisterFragment.class).build());
    }

    @Override
    public void onPhoneLoginClick() {
        start(UIHelper.creat(LoginPhoneFragment.class).build());
    }

    @Override
    public void onWeixinLoginClick() {
        DialogManager.getInstance().showLoadingDialog();
        mShareUtils.login(SHARE_MEDIA.WEIXIN, new ILoginCallback() {
            @Override
            public void onSuccess(Map<String, String> map, SHARE_MEDIA share_media, Map<String, String> map1) {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast(map.toString());
            }

            @Override
            public void onFaild(String s) {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast("取消失败");
            }

            @Override
            public void onCancel() {
                DialogManager.getInstance().dismiss();
                DialogManager.getInstance().toast("取消登录");
            }
        });
    }

    @Override
    public void onLogoClick() {
        DialogManager.getInstance().toast("logo");
    }

    @Override
    public void onAgreementClick() {
        start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL,"http://baidu.com").build());
    }
}
