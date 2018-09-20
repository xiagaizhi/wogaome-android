package com.yushi.leke.fragment.exhibition.voteend;

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
import com.yushi.leke.fragment.exhibition.voteend.allproject.AllendFragment;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(VoteendVu.class)
public class VoteendFragment extends BaseListFragment<VoteendContract.IView> implements VoteendContract.Presenter {
    private MultiTypeAdapter adapter;
    private Voteendinfolist infolist;
    private ICallBack mICallBack;
    private String activityid;

    public void setmICallBack(ICallBack mICallBack) {
        this.mICallBack = mICallBack;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            activityid = bundle.getString(Global.BUNDLE_KEY_ACTIVITYID);
        }
        adapter = new MultiTypeAdapter();
        adapter.register(Voteendinfo.class, new VoteendBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                if (mICallBack != null) {
                    mICallBack.OnBackResult(s);
                }
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
                .getvoteenddata(index, activityid))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist = JSON.parseObject(mApiBean.getData(), Voteendinfolist.class);
                            if (infolist != null && infolist.getProjectList().size() > 0) {
                                list.clear();
                                if (mICallBack != null) {//请求播放第一条视频
                                    Voteendinfo voteendinfo = infolist.getProjectList().get(0);
                                    mICallBack.OnBackResult(voteendinfo.getAliVideoId(), voteendinfo.getTitle(), voteendinfo.getId());
                                }
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
                .getvoteenddata(1, activityid))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist = JSON.parseObject(mApiBean.getData(), Voteendinfolist.class);
                            if (infolist != null && infolist.getProjectList().size() > 0) {
                                list.clear();
                                if (mICallBack != null) {//请求播放第一条视频
                                    Voteendinfo voteendinfo = infolist.getProjectList().get(0);
                                    mICallBack.OnBackResult(voteendinfo.getAliVideoId(), voteendinfo.getTitle(), voteendinfo.getId());
                                }
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
    public void MyCallback() {
        getRootFragment().start(UIHelper.creat(AllendFragment.class)
                .put(Global.BUNDLE_KEY_ACTIVITYID, activityid)
                .build());
    }
}
