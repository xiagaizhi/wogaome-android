package com.yushi.leke.fragment.exhibition.voteing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.man.MANHitBuilders;
import com.alibaba.sdk.android.man.MANPageHitBuilder;
import com.alibaba.sdk.android.man.MANService;
import com.alibaba.sdk.android.man.MANServiceProvider;
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
import com.yushi.leke.fragment.exhibition.vote.VoteFragment;
import com.yushi.leke.fragment.exhibition.voteing.allproject.AllprojectsFragment;
import com.yushi.leke.util.ArgsUtil;

import java.util.HashMap;
import java.util.Map;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(VoteingVu.class)
public class VoteingFragment extends BaseListFragment<VoteingContract.IView> implements VoteingContract.Presenter {
    private MultiTypeAdapter adapter;
    private Voteinginfolist infolist;
    private ICallBack mICallBack;
    private String activityid;
    public void setmICallBack(ICallBack mICallBack) {
        this.mICallBack = mICallBack;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            activityid=bundle.getString(Global.BUNDLE_KEY_ACTIVITYID);
        }
        Log.d("2009",String.valueOf(activityid));
        adapter = new MultiTypeAdapter();
        adapter.register(Voteinginfo.class, new VoteingBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                if (mICallBack != null) {
                    int type = (int) s[0];
                    if (type == 1) {
                        String projectId = (String) s[1];
                        VoteFragment voteFragment = new VoteFragment();
                        Bundle args = new Bundle();
                        args.putString("projectId", projectId);
                        voteFragment.setArguments(args);
                        voteFragment.show(getFragmentManager(), "VoteFragment");
                        ArgsUtil.datapoint(ArgsUtil.VOTE_NAME,"null",ArgsUtil.UID,ArgsUtil.VOTE_CODE,projectId,null);
                    } else if (type == 2) {
                        mICallBack.OnBackResult(s[1], s[2], s[3]);
                    }

                }
            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        getvotedata(getVu().getRecyclerView().getPageManager().getCurrentIndex());
        datapoint();
    }

    @Override
    public void onLoadMore(int index) {
        getvotedata(index);
    }

    /**
     * 请求数据
     */
    private void getvotedata(final int currentPage) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .getvoteingdata(currentPage, activityid))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist = JSON.parseObject(mApiBean.getData(), Voteinginfolist.class);
                            if (infolist != null && infolist.getProjectList().size() > 0) {
                                if (currentPage == 0) {
                                    list.clear();
                                    if (mICallBack != null) {//请求播放第一条视频
                                        Voteinginfo voteinginfo = infolist.getProjectList().get(0);
                                        mICallBack.OnBackResult(voteinginfo.getAliVideoId(), voteinginfo.getTitle(), voteinginfo.getId());
                                    }
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
        getvotedata(getVu().getRecyclerView().getPageManager().getCurrentIndex());
    }

    @Override
    public void MyCallback() {
        getRootFragment().start(UIHelper
                .creat(AllprojectsFragment.class)
                .put(Global.BUNDLE_KEY_ACTIVITYID,activityid)
                .build());
    }
    void datapoint(){
        MANHitBuilders.MANCustomHitBuilder hitBuilder = new MANHitBuilders.MANCustomHitBuilder("playmusic");
        hitBuilder.setEventPage("listen");
        hitBuilder.setProperty("type", "rock");
        hitBuilder.setProperty("title", "wonderful tonight");
        MANService manService = MANServiceProvider.getService();
        manService.getMANAnalytics().getDefaultTracker().send(hitBuilder.build());
    }
}
