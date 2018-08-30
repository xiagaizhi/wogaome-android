package com.yushi.leke.fragment.bindPhone.checkPhone;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    public void getVerifcationCode(String phoneNumber) {

    }

    @Override
    public void checkPhone(String phoneNumber, String code) {

    }
}
