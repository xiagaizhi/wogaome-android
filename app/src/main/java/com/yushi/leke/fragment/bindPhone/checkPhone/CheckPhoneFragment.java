package com.yushi.leke.fragment.bindPhone.checkPhone;

import android.os.Bundle;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.YFApi;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(CheckPhoneVu.class)
public class CheckPhoneFragment extends BaseFragment<CheckPhoneContract.IView> implements CheckPhoneContract.Presenter {
    private String phoneNumber;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            phoneNumber = bundle.getString("phoneNumber");
            getVu().returnPhoneNumber(phoneNumber);
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public boolean getVerifcationCode(String sessionId) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).sendChangeMobileVcode(sessionId))
                    .useCache(false)
                    .enqueue(new BaseHttpCallBack() {
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
            return true;
        }
    }

    @Override
    public void checkPhone(String code) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).validateChangeMobileVcode(code))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        Bundle bundle = new Bundle();
                        bundle.putString("token", "token");
                        setFragmentResult(RESULT_OK, bundle);
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });

    }
}
