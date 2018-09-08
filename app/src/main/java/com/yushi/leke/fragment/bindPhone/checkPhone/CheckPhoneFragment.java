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

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(CheckPhoneVu.class)
public class CheckPhoneFragment extends BaseFragment<CheckPhoneContract.IView> implements CheckPhoneContract.Presenter {
    private String phoneNumber;
    private int type;
    public static final int CHECKPHONE_FROM_ACCOUNTSAFETY = 1;//从账户安全跳转过来
    public static final int CHECKPHONE_FROM_PAYSAFETY = 2;//从支付安全跳转过来

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            phoneNumber = bundle.getString("phoneNumber");
            type = bundle.getInt("type", 0);
            getVu().returnPhoneNumber(phoneNumber);
        }
    }


    @Override
    public void onRefresh() {

    }

    private BaseHttpCallBack baseHttpCallBack = new BaseHttpCallBack() {
        @Override
        public void onSuccess(ApiBean mApiBean) {

        }

        @Override
        public void onError(int id, Exception e) {

        }

        @Override
        public void onFinish() {

        }
    };

    @Override
    public boolean getVerifcationCode(String sessionId) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            if (type == CHECKPHONE_FROM_ACCOUNTSAFETY) {
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).sendChangeMobileVcode(sessionId))
                        .useCache(false)
                        .enqueue(baseHttpCallBack);
            } else if (type == CHECKPHONE_FROM_PAYSAFETY) {
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).sendmobileVcode(phoneNumber, sessionId))
                        .useCache(false)
                        .enqueue(baseHttpCallBack);
            }

            return true;
        }
    }

    private BaseHttpCallBack baseHttpCallBack2 = new BaseHttpCallBack() {
        @Override
        public void onSuccess(ApiBean mApiBean) {
            Bundle bundle = new Bundle();
            try {
                JSONObject jsonObject = new JSONObject(mApiBean.getData());
                bundle.putString("token", jsonObject.getString("token"));
                setFragmentResult(RESULT_OK, bundle);
            } catch (JSONException e) {
                e.printStackTrace();
                pop();
            }

        }

        @Override
        public void onError(int id, Exception e) {

        }

        @Override
        public void onFinish() {
            pop();
        }
    };

    @Override
    public void checkPhone(String code) {


        if (type == CHECKPHONE_FROM_ACCOUNTSAFETY) {
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).validateChangeMobileVcode(code))
                    .useCache(false)
                    .enqueue(baseHttpCallBack2);
        } else if (type == CHECKPHONE_FROM_PAYSAFETY) {
            ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).mobileAndVCode(phoneNumber, code))
                    .useCache(false)
                    .enqueue(baseHttpCallBack2);
        }
    }

    @Override
    public String getPhone() {
        return phoneNumber;
    }
}
