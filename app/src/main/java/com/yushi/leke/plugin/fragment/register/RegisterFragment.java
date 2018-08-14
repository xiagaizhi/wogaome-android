package com.yushi.leke.plugin.fragment.register;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(RegisterVu.class)
public class RegisterFragment extends BaseFragment<RegisterContract.IView> implements RegisterContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }
}
