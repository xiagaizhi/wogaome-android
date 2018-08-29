package com.yushi.leke.fragment.bindPhone;

import android.os.Bundle;

import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(BindPhoneVu.class)
public class BindPhoneFragment extends BaseFragment<BindPhoneContract.IView> implements BindPhoneContract.Presenter {
    private boolean isNeedReturnState;
    private String phoneNumber;
    private boolean isSafetyCheck;//true:安全校验 false:绑定手机

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isNeedReturnState = bundle.getBoolean("isNeedReturnState", false);
            phoneNumber = bundle.getString("phoneNumber", "");
            isSafetyCheck = bundle.getBoolean("isSafetyCheck", false);
            getVu().connectData(isSafetyCheck, isNeedReturnState, phoneNumber);
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void getVerifcationCode(String phone) {

    }

    @Override
    public void bindPhone(String phone, String code) {

    }
}
