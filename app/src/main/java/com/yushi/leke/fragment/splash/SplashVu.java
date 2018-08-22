package com.yushi.leke.fragment.splash;

import android.view.View;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_splash)
@Title("闪屏")
public class SplashVu extends BaseVu<SplashContract.Presenter> implements SplashContract.IView {
    @Override
    public void initView(View view) {

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
