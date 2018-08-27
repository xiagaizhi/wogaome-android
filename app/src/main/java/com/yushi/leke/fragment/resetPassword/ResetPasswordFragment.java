package com.yushi.leke.fragment.resetPassword;

import android.os.Bundle;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.EnhancedCall;
import com.yufan.library.api.remote.YFApi;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yufan.library.util.SoftInputUtil;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(ResetPasswordVu.class)
public class ResetPasswordFragment extends BaseFragment<ResetPasswordContract.IView> implements ResetPasswordContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void getVerifcationCode() {

    }

    @Override
    public void resetPassword(String phone, String vcode, String newPassword) {
        EnhancedCall call= ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).resetPwd(phone,newPassword,vcode));//
        call.enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {

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
    public void onDestroyView() {
        SoftInputUtil.hideSoftInput(getActivity(),getView());
        super.onDestroyView();
    }
}
