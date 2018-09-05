package com.yushi.leke.fragment.bindPhone.checkPhone;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.inject.VuClass;

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
    public boolean getVerifcationCode() {
        if (TextUtils.isEmpty(phoneNumber)) {

            return false;
        } else {
            return true;
        }
    }

    @Override
    public void checkPhone(String code) {
        // TODO: 2018/9/5 返回验证码
        Bundle bundle = new Bundle();
        bundle.putString("verificationCode", "verificationCode");
        setFragmentResult(RESULT_OK, bundle);
    }
}
