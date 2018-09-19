package com.yushi.leke.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
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
        adapter.register(Homeinfo.class,new SubscriptionsViewBinder(new ICallBack() {
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
        getdata(getVu().getRecyclerView().getPageManager().getCurrentIndex());
    }
   private void getdata(final int currentpage){
        ApiManager.getCall( ApiManager.getInstance().create(YFApi.class)
                .showAlbum("channelId",currentpage))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                super.onSuccess(mApiBean);
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    Homeinfolist infolist = JSON.parseObject(mApiBean.getData(), Homeinfolist.class);
                    if (infolist != null && infolist.getList().size() > 0) {
                        if (currentpage== 0) {
                            list.clear();
                            list.add(new SubscriptionBanner());
                        }
                        list.addAll(infolist.getList());
                        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                    } else {
                        vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                    }
                }
            }

        });
    }

    @Override
    public void onLoadMore(int index) {
        getdata(index);
        Toast.makeText(getContext(),String.valueOf(getVu().getRecyclerView().getPageManager().getCurrentIndex()),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        getdata(getVu().getRecyclerView().getPageManager().getCurrentIndex());
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
