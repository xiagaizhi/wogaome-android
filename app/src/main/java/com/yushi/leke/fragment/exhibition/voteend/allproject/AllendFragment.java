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
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.exhibition.voteing.allproject.Allprojectsinfo;
import com.yushi.leke.fragment.exhibition.voteing.allproject.Allprojectsinfolist;
import com.yushi.leke.fragment.exhibition.voteing.allproject.Cityinfolist;
import com.yushi.leke.fragment.exhibition.voteing.allproject.Industryinfolist;
import com.yushi.leke.fragment.exhibition.voteing.seacher.ActivitySeachFragment;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(AllendVu.class)
public class AllendFragment extends BaseListFragment<AllendContract.IView> implements AllendContract.Presenter {
    private MultiTypeAdapter adapter;
    private Allprojectsinfolist infolist;
    private String activityid;
    private int exhibitionType;
    List<String> worklistname =new ArrayList<>();
    List<Long>worklistid=new ArrayList<>();
    List<String>citylist=new ArrayList<>();
    long arg1;
    String arg2;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            activityid = bundle.getString(Global.BUNDLE_KEY_ACTIVITYID);
            exhibitionType=bundle.getInt(Global.BUNDLE_KEY_EXHIBITION_TYE);
        }
        init();
    }


    private void init() {
        adapter = new MultiTypeAdapter();
        adapter.register(Allprojectsinfo.class, new AllendBinder(new ICallBack() {
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
                            infolist = JSON.parseObject(mApiBean.getData(), Allprojectsinfolist.class);
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
        if (getVu().getcity().equals("请选择城市")&&getVu().getindustry()==-1){
            arg1=0;
            arg2="";
        }else if (getVu().getcity().equals("请选择城市")&&getVu().getindustry()!=-1){
            arg1=getVu().getindustry();
            arg2="";
        }else if (!getVu().getcity().equals("请选择城市")&&getVu().getindustry()==-1){
            arg1=0;
            arg2=getVu().getcity();
        }
        else
        {
            arg1=getVu().getindustry();
            arg2=getVu().getcity();
        }
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .getvoteendallpro(1, activityid, arg1, arg2))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            infolist = JSON.parseObject(mApiBean.getData(), Allprojectsinfolist.class);
                            if (infolist != null && infolist.getProjectList().size() > 0) {
                                list.clear();
                                list.addAll(infolist.getProjectList());
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                            } else {
                                list.clear();
                                vu.getRecyclerView().getAdapter().notifyDataSetChanged();
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

    @Override
    public void getCitylist() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class)
                .getcitylist(getactivityid()))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(final ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            Cityinfolist cityinfolist= JSON.parseObject(mApiBean.getData(),Cityinfolist .class);
                            citylist.clear();
                            for (int i = 0; i< cityinfolist.getAddressList().size(); i++){
                                citylist.add(cityinfolist.getAddressList().get(i));
                                getVu().setCitylist(citylist);
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
    public void getWorklist() {
        ApiManager.getCall(ApiManager.getInstance()
                .create(YFApi.class)
                .getindustrylist(getactivityid()))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(final ApiBean mApiBean) {
                        if (!TextUtils.isEmpty(mApiBean.getData())) {
                            Industryinfolist industryinfolist= JSON.parseObject(mApiBean.getData(),Industryinfolist .class);
                            worklistname.clear();
                            worklistid.clear();
                            for (int i = 0; i< industryinfolist.getIndustryList().size(); i++){
                                worklistname.add(industryinfolist.getIndustryList().get(i).getIndustryName());
                                worklistid.add(industryinfolist.getIndustryList().get(i).getIndustryId());
                                getVu().setWorklist(worklistname,worklistid);
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
    public void seacherOnclick() {
        start(UIHelper.creat(ActivitySeachFragment.class)
                .put(Global.BUNDLE_KEY_EXHIBITION_TYE,exhibitionType)
                .put(Global.BUNDLE_KEY_ACTIVITYID, activityid)
                .build());
    }
}
