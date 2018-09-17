package com.yushi.leke.fragment.exhibition.Voteing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.exhibition.Voteing.allproject.allprojectsFragment;
import com.yushi.leke.fragment.exhibition.vote.VoteFragment;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(VoteingVu.class)
public class VoteingFragment extends BaseListFragment<VoteingContract.IView> implements VoteingContract.Presenter {
    private MultiTypeAdapter adapter;
    private Voteinginfolist doendinfo;
    private ICallBack mICallBack;

    public void setmICallBack(ICallBack mICallBack) {
        this.mICallBack = mICallBack;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MultiTypeAdapter();
        Log.d("LOG", "oncreat");
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
                    } else if (type == 2) {
                        mICallBack.OnBackResult(s[1], s[2]);
                    }

                }
            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        getvotedata(getVu().getRecyclerView().getPageManager().getCurrentIndex());
    }

    @Override
    public void onLoadMore(int index) {
        getvotedata(index);
    }


    /**
     * 请求数据
     *
     * @param currentpage
     */
    private void getvotedata(final int currentpage) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getvotedata(currentpage, "1")).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    doendinfo = JSON.parseObject(mApiBean.getData(), Voteinginfolist.class);
                    if (doendinfo != null && doendinfo.getProjectList() != null && doendinfo.getProjectList().size() > 0) {
                        if (currentpage == 0) {
                            list.clear();
                        }
                        if (mICallBack != null) {//请求播放第一条视频
                            mICallBack.OnBackResult(doendinfo.getProjectList().get(0).getAliVideoId(), doendinfo.getProjectList().get(0).getTitle());
                        }
                        list.addAll(doendinfo.getProjectList());
                        getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
                    } else {
                        vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
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
    }


    @Override
    public void onRefresh() {
        getvotedata(getVu().getRecyclerView().getPageManager().getCurrentIndex());
    }

    @Override
    public void MyCallback() {
        getRootFragment().start(UIHelper.creat(allprojectsFragment.class).build());
    }

}
