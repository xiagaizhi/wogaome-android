package com.yushi.leke.fragment.exhibition.exhibitionHome;

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
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.dialog.ShareDialog;
import com.yushi.leke.fragment.browser.BrowserBaseFragment;
import com.yushi.leke.fragment.exhibition.detail.ExhibitionDetailFragment;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionEmptyInfo;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionErrorInfo;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionInfo;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionInfoList;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionJustOneInfo;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionTopInfo;
import com.yushi.leke.fragment.exhibition.exhibitionHome.binder.ExhibitionEmptyBinder;
import com.yushi.leke.fragment.exhibition.exhibitionHome.binder.ExhibitionErrorBinder;
import com.yushi.leke.fragment.exhibition.exhibitionHome.binder.ExhibitionJustOneViewBinder;
import com.yushi.leke.fragment.exhibition.exhibitionHome.binder.ExhibitionTopViewBinder;
import com.yushi.leke.fragment.exhibition.exhibitionHome.binder.ExhibitionViewBinder;

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
        adapter = new MultiTypeAdapter();
        adapter.register(ExhibitionTopInfo.class, new ExhibitionTopViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                int type = (int) s[0];
                switch (type) {
                    case ExhibitionTopViewBinder.MUSIC_EVENT:
                        onMusicMenuClick();
                        break;
                    case ExhibitionTopViewBinder.HISTORY_EVENT:
                        openPastActivities();
                        break;
                }
            }
        }));
        adapter.register(ExhibitionErrorInfo.class, new ExhibitionErrorBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                onRefresh();
            }
        }));
        adapter.register(ExhibitionEmptyInfo.class, new ExhibitionEmptyBinder());
        adapter.register(ExhibitionInfo.class, new ExhibitionViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                ExhibitionInfo info = (ExhibitionInfo) s[0];//活动进度（0--未开始，1--报名中，2--投票中，3--已结束）
                if (info.getActivityProgress() == 0 || info.getActivityProgress() == 1) {//h5详情页面
                    getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getExhibitionDetail(info.getActivityId())).build());
                } else {//原生详情页面
                    getRootFragment().start(UIHelper.creat(ExhibitionDetailFragment.class)
                            .put(Global.BUNDLE_KEY_EXHIBITION_TYE, info.getActivityProgress())
                            .put(Global.BUNDLE_KEY_ACTIVITYID, info.getActivityId())
                            .build());
                }
            }
        }));
        adapter.register(ExhibitionJustOneInfo.class, new ExhibitionJustOneViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                ExhibitionJustOneInfo info = (ExhibitionJustOneInfo) s[0];//活动进度（1--报名中， 2--投票中 并且只有一个活动）
                if (info.getActivityProgress() == 0 || info.getActivityProgress() == 1) {//h5详情页面
                    getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getExhibitionDetail(info.getActivityId())).build());
                } else {//原生详情页面
                    getRootFragment().start(UIHelper.creat(ExhibitionDetailFragment.class)
                            .put(Global.BUNDLE_KEY_EXHIBITION_TYE, info.getActivityProgress())
                            .put(Global.BUNDLE_KEY_ACTIVITYID, info.getActivityId())
                            .build());
                }
            }
        }));
        list.add(new ExhibitionTopInfo());
        list.add(new ExhibitionErrorInfo());
        adapter.setItems(list);
        vu.getRecyclerView().setAdapter(adapter);
        onRefresh();
    }


    @Override
    public void onLoadMore(int index) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).listActivity(index))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            ExhibitionInfoList exhibitionInfoList = JSON.parseObject(mApiBean.getData(), ExhibitionInfoList.class);
                            if (exhibitionInfoList != null && exhibitionInfoList.getList().size() > 0) {
                                list.addAll(exhibitionInfoList.getList());
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                            } else {
                                vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    @Override
    public void onRefresh() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).listActivity(1))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            ExhibitionInfoList exhibitionInfoList = JSON.parseObject(mApiBean.getData(), ExhibitionInfoList.class);
                            if (exhibitionInfoList != null && exhibitionInfoList.getList().size() > 0) {
                                list.clear();
                                list.add(new ExhibitionTopInfo());
                                if (exhibitionInfoList.getList().size() == 1) {
                                    ExhibitionInfo exhibitionInfo = exhibitionInfoList.getList().get(0);
                                    if (exhibitionInfo.getActivityProgress() == 1 || exhibitionInfo.getActivityProgress() == 2) {
                                        ExhibitionJustOneInfo exhibitionJustOneInfo = new ExhibitionJustOneInfo(exhibitionInfo);
                                        list.add(exhibitionJustOneInfo);
                                        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                                        return;
                                    }
                                }
                                list.addAll(exhibitionInfoList.getList());
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                            } else {
                                list.clear();
                                list.add(new ExhibitionTopInfo());
                                list.add(new ExhibitionEmptyInfo());
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                                vu.setStateEmpty();
                                vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                            }
                        } else {
                            vu.setStateEmpty();
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }


    @Override
    public void onMusicMenuClick() {
        Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void openPastActivities() {
        getRootFragment().start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getPastActivities()).build());
    }
}
