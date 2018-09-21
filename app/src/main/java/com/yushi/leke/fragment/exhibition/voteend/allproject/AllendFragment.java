package com.yushi.leke.fragment.exhibition.voteend.allproject;

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
import com.yushi.leke.YFApi;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(AllendVu.class)
public class AllendFragment extends BaseListFragment<AllendContract.IView> implements AllendContract.Presenter {
    private MultiTypeAdapter adapter;
    private Allendinfolist infolist;
    private String activityid;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            activityid = bundle.getString(Global.BUNDLE_KEY_ACTIVITYID);
        }
        init();
    }


    private void init() {
        adapter = new MultiTypeAdapter();
        adapter.register(Allendinfo.class, new AllendBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {

            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        onRefresh();
    }

    @Override
    public void onLoadMore(int index) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .getvoteendallpro(index, activityid, getVu().getindustry(), getVu().getcity()))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist = JSON.parseObject(mApiBean.getData(), Allendinfolist.class);
                            if (infolist != null && infolist.getProjectList().size() > 0) {
                                list.addAll(infolist.getProjectList());
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                            } else {
                                vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                            }
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .getvoteendallpro(1, activityid, getVu().getindustry(), getVu().getcity()))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist = JSON.parseObject(mApiBean.getData(), Allendinfolist.class);
                            if (infolist != null && infolist.getProjectList().size() > 0) {
                                list.clear();
                                list.addAll(infolist.getProjectList());
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
    public String getactivityid() {
        return activityid;
    }
}
