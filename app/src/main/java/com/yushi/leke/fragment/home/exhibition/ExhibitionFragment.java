package com.yushi.leke.fragment.home.exhibition;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.fragment.home.SubscriptionBanner;
import com.yushi.leke.fragment.home.SubscriptionInfo;
import com.yushi.leke.fragment.home.SubscriptionsViewBinder;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(ExhibitionVu.class)
public class ExhibitionFragment extends BaseListFragment<ExhibitionContract.IView> implements ExhibitionContract.Presenter {
    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(ExhibitionTopInfo.class,new ExhibitionTopViewBinder());
        adapter.register(ExhibitionInfo.class,new ExhibitionViewBinder());
        vu.getRecyclerView().setAdapter(adapter);
        list.add(new ExhibitionTopInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        list.add(new ExhibitionInfo());
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMore(int index) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVu().getRecyclerView().getPTR().refreshComplete();
                getVu(). getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NONE);
            }
        },1000);
    }

    private Handler handler=new Handler();
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getVu().getRecyclerView().getPTR().refreshComplete();
                getVu(). getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NONE);
            }
        },1000);
    }
}
