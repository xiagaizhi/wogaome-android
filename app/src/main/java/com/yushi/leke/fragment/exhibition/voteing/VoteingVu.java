package com.yushi.leke.fragment.exhibition.voteing;

import com.yushi.leke.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.xx_doend_main)
public class VoteingVu extends BaseListVu<VoteingContract.Presenter> implements VoteingContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    @FindView(R.id.sdv)
    ImageView sdv;
    TextView tv;
    @Override
    public void initView(View view) {
        super.initView(view);
        sdv.setVisibility(View.GONE);
        tv= (TextView) findViewById(R.id.tv_vote_checkall);
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.allprojectOnclick();
            }
        });
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
