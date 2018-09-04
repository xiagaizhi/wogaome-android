package com.yushi.leke.fragment.bindPhone.updatePhone;

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
@VuClass(UpdatePhoneVu.class)
public class UpdatePhoneFragment extends BaseFragment<UpdatePhoneContract.IView> implements UpdatePhoneContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void getVerifcationCode(String phone) {

    }

    @Override
    public void updatePhone(String phone, String code) {


        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber", phone);
        setFragmentResult(RESULT_OK, bundle);
    }
}
