package com.yushi.leke.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.UIHelper;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.fragment.searcher.SearchFragment;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SubscriptionsVu.class)
public class SubscriptionsFragment extends BaseListFragment<SubscriptionsContract.IView> implements SubscriptionsContract.Presenter {

    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(SubscriptionBanner.class,new SubscriptionsBannerViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                switch ((int)s[0]){
                    case SubscriptionsBannerViewBinder.BANNER_BINDER_MUSIC:
                        Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
                        startActivity(intent);
                        break;
                    case SubscriptionsBannerViewBinder.BANNER_BINDER_SEARCH:
                        getRootFragment().start(UIHelper.creat(SearchFragment.class).build());
                        break;
                }
            }
        }));
        adapter.register(SubscriptionInfo.class,new SubscriptionsViewBinder());
        vu.getRecyclerView().setAdapter(adapter);
        list.add(new SubscriptionBanner());
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(false));
        list.add(new SubscriptionInfo(true));
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
