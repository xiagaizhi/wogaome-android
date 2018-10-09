package com.yushi.leke.fragment.exhibition.voteend;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.widget.StateLayout;
import com.yushi.leke.R;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.xx_doend_main)
public class VoteendVu extends BaseListVu<VoteendContract.Presenter> implements VoteendContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    @FindView(R.id.sdv)
    ImageView sdv;
    @FindView(R.id.tv_vote_checkall)
    TextView tv_vote_checkall;

    @Override
    public void initView(View view) {
        super.initView(view);
        tv_vote_checkall.setOnClickListener(this);
        sdv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_vote_checkall:
                mPersenter.allprojectOnclick();
                break;
            case R.id.sdv:
                mPersenter.openWinPage();
                break;
        }
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
