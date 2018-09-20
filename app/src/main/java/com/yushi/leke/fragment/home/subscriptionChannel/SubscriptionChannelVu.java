package com.yushi.leke.fragment.home.subscriptionChannel;

import com.yushi.leke.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
@Title("乐客")
public class SubscriptionChannelVu extends BaseListVu<SubscriptionChannelContract.Presenter> implements SubscriptionChannelContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    private TextView titleName;

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        titleName = appToolbar.creatCenterView(TextView.class);
        titleName.setText(mPersenter.getChannelName());
        ImageView leftView=  appToolbar.creatLeftView(ImageView.class);
        leftView.setImageResource(com.yufan.library.R.drawable.left_back_black_arrows);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.onBackPressed();
            }
        });
        appToolbar.build();
        return true;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }

}
