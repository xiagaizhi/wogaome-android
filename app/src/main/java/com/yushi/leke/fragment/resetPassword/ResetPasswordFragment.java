package com.yushi.leke.fragment.resetPassword;

import android.os.Bundle;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.EnhancedCall;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.SoftInputUtil;
import com.yushi.leke.YFApi;

import java.util.Map;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void getVerifcationCode(String phone, String sessionID) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).sendResetPwdVcode(phone,sessionID)).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                DialogManager.getInstance().toast("发送成功");
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
    public void resetPassword(String phone, String vcode, String newPassword) {
        EnhancedCall call= ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).resetPwd(phone,newPassword,vcode));//
        call.enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                DialogManager.getInstance().toast("修改成功！");
                pop();
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
