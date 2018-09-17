package com.yushi.leke.fragment.exhibition.voteing.allproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
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
    public void Getmore() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getvoteallpro(0,"1")).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    infolist= JSON.parseObject(mApiBean.getData(), Allprojectsinfolist.class);
                    list.addAll(infolist.getProjectList());
                    Log.d("300",String.valueOf(infolist.getProjectList().size()));
                    getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
                }

            }
            @Override
            public void onError(int id, Exception e) {
                Log.d("2000","error!!!!!!!!");
            }
            @Override
            public void onFinish() {
            }
        });
    }

    @Override
    public void GetmoreFromwork(String industry) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getvoteworkpro(0,"1",industry)).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                list.removeAll(list);
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    infolist= JSON.parseObject(mApiBean.getData(), Allprojectsinfolist.class);
                    list.addAll(infolist.getProjectList());
                }
                getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
            }
            @Override
            public void onError(int id, Exception e) {
                Log.d("2000","error!!!!!!!!");
            }
            @Override
            public void onFinish() {
            }
        });
    }

    @Override
    public void Getmorefrocity() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getvoteallpro(0,"1")).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    list.removeAll(list);
                    infolist= JSON.parseObject(mApiBean.getData(), Allprojectsinfolist.class);
                    list.addAll(infolist.getProjectList());
                    Log.d("300",String.valueOf(infolist.getProjectList().size()));
                    getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
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
    public void onLoadMore(int index) {
    }
    @Override
    public void onRefresh() {

    }
    void init(){
        adapter=new MultiTypeAdapter();
        adapter.register(Allprojectsinfo.class,new AllprojectBinder());
        Getmore();
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }

}
