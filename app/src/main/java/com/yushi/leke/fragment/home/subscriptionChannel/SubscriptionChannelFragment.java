package com.yushi.leke.fragment.home.subscriptionChannel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.album.AlbumDetailFragment;
import com.yushi.leke.fragment.home.Homeinfo;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zhanyangyang on 18/8/2.
 */
@VuClass(SubscriptionChannelVu.class)
public class SubscriptionChannelFragment extends BaseListFragment<SubscriptionChannelContract.IView> implements SubscriptionChannelContract.Presenter {
    private long channelId;
    private String channelName;
    private MultiTypeAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MultiTypeAdapter();
        adapter.register(Homeinfo.class, new SubscriptionChannelViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                Homeinfo info = (Homeinfo) s[0];
                getRootFragment().start(UIHelper.creat(AlbumDetailFragment.class)
                        .put(Global.BUNDLE_KEY_ALBUMID, info.getAlbumId())
                        .build());
            }
        }));
        adapter.setItems(list);
        getVu().getRecyclerView().setAdapter(adapter);
        onRefresh();
    }

    @Override
    public void getBundleDate() {
        super.getBundleDate();
        Bundle bundle = getArguments();
        channelId = bundle.getLong(Global.BUNDLE_CHANNEL_ID);
        channelName = bundle.getString(Global.BUNDLE_CHANNEL_NAME);
    }

    @Override
    public void onLoadMore(int index) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).showAlbum(channelId, index))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        SubscriptionChannellist mySubscriptionInfos = JSON.parseObject(mApiBean.getData(), SubscriptionChannellist.class);
                        if (mySubscriptionInfos != null && mySubscriptionInfos.getList().size() > 0) {
                            list.addAll(mySubscriptionInfos.getList());
                            vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                        } else {
                            vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                        }
                    }
                });
    }


    @Override
    public void onRefresh() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).showAlbum(channelId, 1))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        SubscriptionChannellist mySubscriptionInfos = JSON.parseObject(mApiBean.getData(), SubscriptionChannellist.class);
                        if (mySubscriptionInfos != null && mySubscriptionInfos.getList().size() > 0) {
                            list.clear();
                            list.addAll(mySubscriptionInfos.getList());
                            vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                        } else {
                            vu.setStateEmpty();
                            vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                        }
                    }
                });
    }

    @Override
    public String getChannelName() {
        return channelName;
    }
}
