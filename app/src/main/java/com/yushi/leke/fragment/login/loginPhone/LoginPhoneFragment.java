package com.yushi.leke.fragment.login.loginPhone;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.EnhancedCall;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.manager.SPManager;
import com.yufan.library.manager.UserManager;
import com.yufan.library.util.SoftInputUtil;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.App;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.browser.BrowserBaseFragment;
import com.yushi.leke.fragment.login.LoginFragment;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.fragment.register.RegisterFragment;
import com.yushi.leke.fragment.resetPassword.ResetPasswordFragment;
import com.yushi.leke.util.ArgsUtil;


/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(LoginPhoneVu.class)
public class LoginPhoneFragment extends BaseFragment<LoginPhoneContract.IView> implements LoginPhoneContract.Presenter {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onRefresh() {
    }



    @Override
    public void onRegister() {
        start(UIHelper.creat(RegisterFragment.class).build());
    }

    @Override
    public void onForgetPassword() {

        start(UIHelper.creat(ResetPasswordFragment.class).build());

    }

    @Override
    public void onClearPhone() {

    }

    @Override
    public void onClearPassword() {

    }

    @Override
    public void login(final String phone, String password) {
        DialogManager.getInstance().showLoadingDialog();
        EnhancedCall call= ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).loginViaPwd(phone,password));//
        call.enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
              JSONObject jsonObject= JSON.parseObject(mApiBean.getData());
             UserManager.getInstance().setToken(jsonObject.getString("token"));
              UserManager.getInstance().setUid(jsonObject.getString("uid"));
                startWithPopTo(UIHelper.creat(MainFragment.class).build(), LoginFragment.class,true);
                //绑定用户和信鸽token
                if (SPManager.getInstance().getString("XGTOKEN","")!=null){
                    binddeviceanduser(SPManager.getInstance().getString("XGTOKEN",""));
                }
                //用户手机登陆数据埋点
                ArgsUtil.datapoint("0301","uid",jsonObject.getString("uid"));
            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {
                DialogManager.getInstance().dismiss();
            }


        });
    }
    private void binddeviceanduser(String token){
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .binddeviceanduser(token))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (mApiBean.getCode().equals(2000)){
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
    public void onAgreementClick() {
        start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL,ApiManager.getInstance().getApiConfig().getProtocol()).build());
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        SoftInputUtil.hideSoftInput(getActivity(),getView());
    }

}
