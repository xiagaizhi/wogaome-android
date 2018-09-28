package com.yushi.leke.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
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
import com.yushi.leke.fragment.browser.BrowserBaseFragment;
import com.yushi.leke.fragment.exhibition.detail.ExhibitionDetailFragment;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionEmptyInfo;
import com.yushi.leke.fragment.exhibition.exhibitionHome.binder.ExhibitionEmptyBinder;
import com.yushi.leke.fragment.exhibition.exhibitionHome.binder.ExhibitionErrorBinder;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionErrorInfo;
import com.yushi.leke.fragment.home.bean.BannerItemInfo;
import com.yushi.leke.fragment.home.bean.Homeinfo;
import com.yushi.leke.fragment.home.bean.SubscriptionChannelInfo;
import com.yushi.leke.fragment.home.bean.SubscriptionColumnInfo;
import com.yushi.leke.fragment.home.binder.SubscriptionBanner;
import com.yushi.leke.fragment.home.binder.SubscriptionTitle;
import com.yushi.leke.fragment.home.binder.SubscriptionsBannerViewBinder;
import com.yushi.leke.fragment.home.binder.SubscriptionsColumnViewBinder;
import com.yushi.leke.fragment.home.binder.SubscriptionsTitleViewBinder;
import com.yushi.leke.fragment.home.binder.SubscriptionsViewBinder;
import com.yushi.leke.fragment.home.subscriptionChannel.SubscriptionChannelFragment;
import com.yushi.leke.fragment.searcher.SearchFragment;
import com.yushi.leke.fragment.splash.advert.NativeJumpInfo;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SubscriptionsVu.class)
public class SubscriptionsFragment extends BaseListFragment<SubscriptionsContract.IView> implements SubscriptionsContract.Presenter {
    private MultiTypeAdapter adapter;
    private SubscriptionBanner subscriptionBanner;
    private SubscriptionTitle subscriptionTitle;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MultiTypeAdapter();
        adapter.register(SubscriptionTitle.class,new SubscriptionsTitleViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                switch ((int) s[0]) {
                    case SubscriptionsBannerViewBinder.BANNER_BINDER_MUSIC:
                        onMusicMenuClick();
                        break;
                    case SubscriptionsBannerViewBinder.BANNER_BINDER_SEARCH:
                        onSearchBarClick();
                        break;
                }
            }
        }));
        adapter.register(SubscriptionBanner.class, new SubscriptionsBannerViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                switch ((int) s[0]) {
                    case SubscriptionsBannerViewBinder.BANNER_BINDER_ITEM:
                        int position = (int) s[1];
                        if (subscriptionBanner != null && subscriptionBanner.getBannerItemInfos() != null && subscriptionBanner.getBannerItemInfos().size() > position) {
                            BannerItemInfo bannerItemInfo = subscriptionBanner.getBannerItemInfos().get(position);
                            if (bannerItemInfo != null) {
                                NativeJumpInfo nativeJumpInfo = bannerItemInfo.getNativeUrl();
                                if (nativeJumpInfo != null) {//0：活动 1：专辑
                                    if (TextUtils.equals("0", nativeJumpInfo.getDetailType())) {
                                        getRootFragment().start(UIHelper
                                                .creat(AlbumDetailFragment.class)
                                                .put(Global.BUNDLE_KEY_ALBUMID, nativeJumpInfo.getDetailId())
                                                .build());
                                    } else if (TextUtils.equals("1", nativeJumpInfo.getDetailType())) {
                                        if (nativeJumpInfo.getActivityProgress() == 0 || nativeJumpInfo.getActivityProgress() == 1) {//h5详情页面
                                            getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getExhibitionDetail(nativeJumpInfo.getDetailId())).build());
                                        } else {//原生详情页面
                                            getRootFragment().start(UIHelper.creat(ExhibitionDetailFragment.class)
                                                    .put(Global.BUNDLE_KEY_EXHIBITION_TYE, nativeJumpInfo.getActivityProgress())
                                                    .put(Global.BUNDLE_KEY_ACTIVITYID, nativeJumpInfo.getDetailId())
                                                    .build());
                                        }
                                    }
                                } else {
                                    if (!TextUtils.isEmpty(bannerItemInfo.getH5Url())) {
                                        getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, bannerItemInfo.getH5Url()).build());
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }));
        adapter.register(Homeinfo.class, new SubscriptionsViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                Homeinfo info = (Homeinfo) s[0];
                getRootFragment().start(UIHelper.creat(AlbumDetailFragment.class)
                        .put(Global.BUNDLE_KEY_ALBUMID, info.getAlbumId())
                        .build());
            }
        }));

        adapter.register(SubscriptionColumnInfo.class, new SubscriptionsColumnViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                SubscriptionColumnInfo subscriptionColumnInfo = (SubscriptionColumnInfo) s[0];
                getRootFragment().start(UIHelper.creat(SubscriptionChannelFragment.class)
                        .put(Global.BUNDLE_CHANNEL_NAME, subscriptionColumnInfo.getChannelName())
                        .put(Global.BUNDLE_CHANNEL_ID, subscriptionColumnInfo.getChannelId()).build());
            }
        }));
        adapter.register(ExhibitionErrorInfo.class, new ExhibitionErrorBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                onRefresh();
            }
        }));
        subscriptionBanner = new SubscriptionBanner();
        subscriptionTitle = new SubscriptionTitle();
        list.add(subscriptionTitle);
        list.add(new ExhibitionErrorInfo());
        adapter.setItems(list);
        vu.getRecyclerView().setAdapter(adapter);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).showBanners())
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            List<BannerItemInfo> banners = JSON.parseArray(mApiBean.getData(), BannerItemInfo.class);
                            if (banners != null && banners.size() > 0) {
                                list.add(subscriptionBanner);
                                if (subscriptionBanner.getBannerItemInfos() == null) {
                                    subscriptionBanner.setBannerItemInfos(new ArrayList<BannerItemInfo>());
                                }
                                subscriptionBanner.getBannerItemInfos().clear();
                                subscriptionBanner.setBannerItemInfos(banners);
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .showChannel())
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            List<SubscriptionChannelInfo> subscriptionChannelInfos = JSON.parseArray(mApiBean.getData(), SubscriptionChannelInfo.class);
                            if (subscriptionChannelInfos != null && subscriptionChannelInfos.size() > 0) {
                                list.clear();
                                list.add(subscriptionTitle);
                                list.add(subscriptionBanner);
                                for (SubscriptionChannelInfo temp : subscriptionChannelInfos) {
                                    SubscriptionColumnInfo subscriptionColumnInfo = new SubscriptionColumnInfo();
                                    subscriptionColumnInfo.setChannelId(temp.getChannelId());
                                    subscriptionColumnInfo.setChannelName(temp.getChannelName());
                                    subscriptionColumnInfo.setMore(temp.getMore());
                                    list.add(subscriptionColumnInfo);
                                    list.addAll(temp.getAlbumViewInfoList());
                                }
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                            } else {
                                vu.setStateEmpty();
                                vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                            }
                        }else {
                            vu.setStateEmpty();
                        }
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
