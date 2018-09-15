package com.yushi.leke.fragment.exhibition;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.yushi.leke.fragment.exhibition.detail.ExhibitionDetailFragment;

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
                if ((int) s[0] == ExhibitionTopViewBinder.MUSIC_EVENT) {
                    onMusicMenuClick();
                }
            }
        }));
        adapter.register(ExhibitionInfo.class, new ExhibitionViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                ExhibitionInfo info = (ExhibitionInfo) s[0];
                // TODO: 2018/9/15 根据不同类型进入不同详情页面
                getRootFragment().start(UIHelper.creat(ExhibitionDetailFragment.class).put(Global.BUNDLE_KEY_EXHIBITION_TYE, Global.EXHIBITION_TYE_VOTING).build());
            }
        }));
        list.add(new ExhibitionTopInfo());
        adapter.setItems(list);
        vu.getRecyclerView().setAdapter(adapter);
        getActivityList(getVu().getRecyclerView().getPageManager().getCurrentIndex());
    }


    private void getActivityList(final int currentPage) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).listActivity(currentPage))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            ExhibitionInfoList exhibitionInfoList = JSON.parseObject(mApiBean.getData(), ExhibitionInfoList.class);
                            if (exhibitionInfoList != null && exhibitionInfoList.getList().size() > 0) {
                                if (currentPage == 0) {
                                    list.clear();
                                    list.add(new ExhibitionTopInfo());
                                }
                                list.addAll(exhibitionInfoList.getList());
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
        getActivityList(index);
    }

    @Override
    public void onRefresh() {
        getActivityList(getVu().getRecyclerView().getPageManager().getCurrentIndex());
    }

    @Override
    public void onMusicMenuClick() {
        Intent intent = new Intent(getContext(), MusicPlayerActivity.class);
        startActivity(intent);
    }
}
