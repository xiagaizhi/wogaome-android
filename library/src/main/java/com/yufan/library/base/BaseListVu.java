package com.yufan.library.base;

import android.view.View;
import android.view.ViewGroup;

import com.yufan.library.inter.Pr;
import com.yufan.library.inter.VuList;
import com.yufan.library.view.recycler.PageInfo;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yufan.library.widget.StateLayout;

/**
 * Created by mengfantao on 18/7/9.
 */

public abstract class BaseListVu <T extends Pr>extends BaseVu implements VuList{

    protected T mPersenter;

    @Override
    public void initView(View view) {
        initRecyclerview(getRecyclerView());
    }

    @Override
    public final T getPresenter() {
        return mPersenter;
    }
    @Override
    public final void setPresenter(Object presenter) {
        mPersenter= (T) presenter;
    }
    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        stateLayout.getEmptyView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.onRefresh();
            }
        });
        stateLayout.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.onRefresh();
            }
        });
    }
    protected  final   void initRecyclerview(final YFRecyclerView recyclerViewModel){
        recyclerViewModel.initPTR();
        recyclerViewModel.setOnPagerListener(new YFRecyclerView.OnPagerListener() {
            @Override
            public void onLoadMore(int index) {
                if (recyclerViewModel.getPageManager().isIdle()) {
                    recyclerViewModel.getPageManager().setPageState(PageInfo.PAGE_STATE_LOADING);
                   mPersenter.onLoadMore(index);
                }
            }
            @Override
            public void onRefresh() {
                if (recyclerViewModel.getPageManager().isIdle()) {
                    recyclerViewModel.getPageManager().resetIndex();
                    recyclerViewModel.getPageManager().setPageState(PageInfo.PAGE_STATE_LOADING);
                    mPersenter.onRefresh();
                }
            }
        });
        mStateLayout = new StateLayout(this);
        mStateLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initStatusLayout(mStateLayout);
        recyclerViewModel.setEmptyView(mStateLayout);
    }
    protected final   void initState() {
    }

}
