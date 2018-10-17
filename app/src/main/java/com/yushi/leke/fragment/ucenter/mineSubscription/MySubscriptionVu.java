package com.yushi.leke.fragment.ucenter.mineSubscription;

import com.yushi.leke.R;

import android.view.View;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;

/**
 * Created by zhanyangyang on 18/8/2.
 */
@FindLayout(layout = R.layout.layout_fragment_list)
@Title("我的订阅")
public class MySubscriptionVu extends BaseListVu<MySubscriptionContract.Presenter> implements MySubscriptionContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;



    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return super.initTitle(appToolbar);
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
