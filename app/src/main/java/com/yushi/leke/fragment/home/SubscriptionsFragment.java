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
import com.yushi.leke.fragment.album.AlbumDetailFragment;
import com.yushi.leke.fragment.album.audioList.MediaBrowserFragment;
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
                        onMusicMenuClick();
                        break;
                    case SubscriptionsBannerViewBinder.BANNER_BINDER_SEARCH:
                        onSearchBarClick();
                        break;
                        case SubscriptionsBannerViewBinder.BANNER_BINDER_ITEM:
                            getRootFragment().start(UIHelper.creat(AlbumDetailFragment.class).build());

                            break;
                }
            }
        }));
        adapter.register(SubscriptionInfo.class,new SubscriptionsViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                getRootFragment().start(UIHelper.creat(AlbumDetailFragment.class).build());
            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        list.add(new SubscriptionBanner());
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0830/20180830040720965.jpg"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0823/20180823105708870.png"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0830/20180830040720965.jpg"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0823/20180823105708870.png"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0830/20180830040720965.jpg"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0823/20180823105708870.png"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0830/20180830040720965.jpg"));
        list.add(new SubscriptionInfo(false,"http://oss.cyzone.cn/2018/0823/20180823105708870.png"));
        list.add(new SubscriptionInfo(true,"http://oss.cyzone.cn/2018/0830/20180830040720965.jpg"));
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

    @Override
    public void onMusicMenuClick() {
        Intent intent = new Intent(getActivity(), MusicPlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSearchBarClick() {
        getRootFragment().start(UIHelper.creat(SearchFragment.class).build());
    }
}
