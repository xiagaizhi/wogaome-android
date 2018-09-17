package com.yushi.leke.fragment.exhibition.voteing.allproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.YFApi;

import me.drakeet.multitype.MultiTypeAdapter;
/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(AllprojectsVu.class)
public class AllprojectsFragment extends BaseListFragment<AllprojectsContract.IView> implements AllprojectsContract.Presenter {
    MultiTypeAdapter adapter;
    Allprojectsinfolist infolist;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }
    @Override
    public void onLoadMore(int index) {
        getalldata(index,"1",getVu().getindustry(),getVu().getcity());
    }
    @Override
    public void onRefresh() {
        getalldata(getVu().getRecyclerView().getPageManager().getCurrentIndex(),"1",getVu().getindustry(),getVu().getcity());
    }
    /*
    *获取数据
     */
    private void getalldata(final int currentPage, String activityid, final String industry, final String city) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .getvoteallpro(currentPage,activityid,industry,city))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist= JSON.parseObject(mApiBean.getData(), Allprojectsinfolist.class);
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
    void init(){
        adapter=new MultiTypeAdapter();
        adapter.register(Allprojectsinfo.class,new AllprojectBinder());
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
        getalldata(getVu().getRecyclerView().getPageManager().getCurrentIndex(),"1",null,null);
    }

}
