package com.yushi.leke.fragment.exhibition.voteend;

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
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.exhibition.voteing.Voteinginfolist;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(VoteendVu.class)
public class VoteendFragment extends BaseListFragment<VoteendContract.IView> implements VoteendContract.Presenter {
    private MultiTypeAdapter adapter;
    private Voteendinfolist infolist;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(Voteendinfo.class,new VoteendBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {

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


    @Override
    public void onRefresh() {
        getvotedata(getVu().getRecyclerView().getPageManager().getCurrentIndex());
    }
    /**
     * 请求数据
     *
     *
     */
    private void getvotedata(final int currentPage) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .getvoteenddata(currentPage,"1"))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist= JSON.parseObject(mApiBean.getData(), Voteendinfolist.class);
                            if (infolist != null && infolist.getProjectList().size() > 0) {
                                if (currentPage == 0) {
                                    list.clear();
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

    }
}
