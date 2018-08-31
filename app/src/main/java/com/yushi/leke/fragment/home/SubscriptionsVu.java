package com.yushi.leke.fragment.home;

import com.yushi.leke.R;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.UIHelper;
import com.yushi.leke.activity.MusicPlayerActivity;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.layout_fragment_list)
@Title("订阅专栏")
public class SubscriptionsVu extends BaseListVu<SubscriptionsContract.Presenter> implements SubscriptionsContract.IView {
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
    public boolean initTitle(AppToolbar appToolbar) {

        appToolbar.build();
        return true;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
