package com.yushi.leke.fragment.setting.modifyLoginPwd;

import android.os.Bundle;

import com.google.android.gms.common.api.Api;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.manager.DialogManager;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.YFApi;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(ModifyLoginPwdVu.class)
public class ModifyLoginPwdFragment extends BaseFragment<ModifyLoginPwdContract.IView> implements ModifyLoginPwdContract.Presenter {
    private String phoneNumber;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            phoneNumber = bundle.getString("phoneNumber");
            getVu().updataPhoneNumber(phoneNumber);
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void getVerifcationCode() {
        DialogManager.getInstance().showLoadingDialog();
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).sendChangePwdVcode()).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                DialogManager.getInstance().toast("发送成功");
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

    @Override
    public void modifyLoginPwd(String code, String password) {
        DialogManager.getInstance().showLoadingDialog();
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).changePwdViaVcode(code,password)).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                DialogManager.getInstance().toast("修改成功");
                pop();
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


}
