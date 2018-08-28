package com.yushi.leke.fragment.ucenter;

import android.content.Intent;
import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.activity.QuarantineActivity;
import com.yushi.leke.uamp.ui.MediaBrowserFragment;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(UCenterVu.class)
public class UCenterFragment extends BaseFragment<UCenterContract.IView> implements UCenterContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void startPlayer() {
        Intent intent=new Intent(getContext(),QuarantineActivity.class);
        startActivity(intent);

    }

    @Override
    public void startPlayerList() {
        getRootFragment().start(UIHelper.creat(MediaBrowserFragment.class).build());
    }
}
