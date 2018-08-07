package com.yushi.leke.plugin.operation.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(PlayerVu.class)
public class PlayerFragment extends BaseFragment<PlayerContract.View> implements PlayerContract.Presenter {


    @Override
    public void onRefresh() {


    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public boolean onBackPressedSupport() {

        return super.onBackPressedSupport();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
