package com.yushi.leke.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.fragment.album.AlbumDetailFragment;
import com.yushi.leke.fragment.exhibition.exhibitionHome.ExhibitionErrorBinder;
import com.yushi.leke.fragment.exhibition.exhibitionHome.ExhibitionErrorInfo;
import com.yushi.leke.fragment.exhibition.exhibitionHome.ExhibitionTopInfo;
import com.yushi.leke.fragment.searcher.SearchActionInfo;
import com.yushi.leke.fragment.searcher.SearchBottomInfo;
import com.yushi.leke.fragment.searcher.SearchFragment;

import java.util.List;

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
        adapter.register(AudioInfo.class,new SubscriptionsViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                getRootFragment().start(UIHelper.creat(AlbumDetailFragment.class).build());
            }
        }));
        adapter.register(ExhibitionErrorInfo.class,new ExhibitionErrorBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                onRefresh();
            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        list.add(new SubscriptionBanner());
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        onRefresh();
    }


    @Override
    public void onLoadMore(int index) {
        ApiManager.getCall( ApiManager.getInstance().create(YFApi.class).showAlbum("channelId",getVu().getRecyclerView().getPageManager().getCurrentIndex()+"")).enqueue(new YFListHttpCallBack(vu) {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                JSONObject jsonObject= JSON.parseObject(mApiBean.data);
                List<AudioInfo> actionInfos= JSON.parseArray(jsonObject.getString("list"),AudioInfo.class);
                list.addAll(actionInfos);
            }

        });
    }

    @Override
    public void onRefresh() {

        ApiManager.getCall( ApiManager.getInstance().create(YFApi.class).showAlbum("channelId",getVu().getRecyclerView().getPageManager().getCurrentIndex()+"")).enqueue(new YFListHttpCallBack(vu) {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                super.onSuccess(mApiBean);
                JSONObject jsonObject= JSON.parseObject(mApiBean.data);
                List<AudioInfo> actionInfos= JSON.parseArray(jsonObject.getString("list"),AudioInfo.class);
                list.clear();
                list.add(new SubscriptionBanner());
                list.addAll(actionInfos);
            }

        });

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
