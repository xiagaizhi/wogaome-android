package com.yushi.leke.fragment.setting.modifyLoginPwd;

import android.os.Bundle;

import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;

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
    public boolean getVerifcationCode() {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void modifyLoginPwd(String code) {

    }
}
