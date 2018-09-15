package com.yushi.leke.fragment.exhibition.voteing.allproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.bean.LocationBean;
import com.yufan.library.inject.VuClass;
import com.yufan.library.util.AreaUtil;
import com.yushi.leke.YFApi;

import me.drakeet.multitype.MultiTypeAdapter;
/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(allprojectsVu.class)
public class allprojectsFragment extends BaseListFragment<allprojectsContract.IView> implements allprojectsContract.Presenter {
    MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).allprojects("1")).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {

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
    public String selectedCityInfo(int options1, int options2) {
        LocationBean province = AreaUtil.getInstance().getOptions1Items().get(options1);
        LocationBean city = AreaUtil.getInstance().getOptions2Items().get(options1).get(options2);
        return city.getName();
    }
    @Override
    public void onLoadMore(int index) {

    }


    @Override
    public void onRefresh() {

    }
    void init(){
        adapter=new MultiTypeAdapter();
        adapter.register(allprojectsinfo.class,new allprojectBinder());
        allprojectsinfo info=new allprojectsinfo("http://oss.cyzone.cn/2018/0830/20180830040720965.jpg");
        for (int i=0;i<3;i++){
            list.add(info);
        }
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }

}
