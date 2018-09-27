package com.yushi.leke.fragment.exhibition.voteing.seacher;

import android.os.Bundle;

import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(ActivitySeacherVu.class)
public class ActivitySeacherFragment extends BaseFragment<ActivitySeacherContract.IView> implements ActivitySeacherContract.Presenter {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {

    }
}
