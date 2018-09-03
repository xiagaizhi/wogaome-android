package com.yushi.leke.fragment.bindPhone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.Global;
import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(BindPhoneVu.class)
public class BindPhoneFragment extends BaseFragment<BindPhoneContract.IView> implements BindPhoneContract.Presenter {

    @Override
    public void onRefresh() {

    }

    @Override
    public void getVerifcationCode(String phone) {

    }

    @Override
    public void bindPhone(String phone, String code, String pwd) {
        Bundle bundle = new Bundle();
        bundle.putString("phoneNumber", phone);
        setFragmentResult(RESULT_OK, bundle);
    }
}
