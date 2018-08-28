package com.yushi.leke.fragment.roadShow;

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
@VuClass(RoadshowHallVu.class)
public class RoadshowHallFragment extends BaseFragment<RoadshowHallContract.IView> implements RoadshowHallContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }
}
