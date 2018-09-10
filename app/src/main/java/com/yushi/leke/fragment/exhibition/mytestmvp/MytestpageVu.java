package com.yushi.leke.fragment.exhibition.mytestmvp;

import com.yushi.leke.R;

import android.view.View;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.xx_mainmytestview)
public class MytestpageVu extends BaseListVu<MytestpageContract.Presenter> implements MytestpageContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) { return false; }
    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
