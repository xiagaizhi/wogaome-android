package com.yushi.leke.fragment.splash.guide;

import android.content.Intent;
import android.os.Bundle;

import com.yufan.library.Global;
import com.yufan.library.base.BaseApplication;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.WindowManager;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.fragment.splash.advert.AdFragmentFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(GuideFragmentVu.class)
public class GuideFragmentFragment extends BaseFragment<GuideFragmentContract.IView> implements GuideFragmentContract.Presenter {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void jumpToMain() {
        if (_mActivity != null && !_mActivity.isFinishing()) {
            WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            _mActivity.getWindow().setAttributes(lp);
            _mActivity.getWindow().setBackgroundDrawableResource(R.color.white);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().remove(GuideFragmentFragment.this).commit();
            Intent filter = new Intent();
            filter.setAction(Global.BROADCAST_ACTION_UPGRADE);
            LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(filter);
        }
    }
}
