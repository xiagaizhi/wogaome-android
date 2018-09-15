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
import com.yufan.library.bean.LocationBean;
import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.AreaUtil;
import com.yushi.leke.YFApi;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;
/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(AllprojectsVu.class)
public class AllprojectsFragment extends BaseListFragment<AllprojectsContract.IView> implements AllprojectsContract.Presenter {
    MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
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
        Allprojectsinfo info=new Allprojectsinfo("http://oss.cyzone.cn/2018/0830/20180830040720965.jpg");
        for (int i=0;i<3;i++){
            list.add(info);
        }
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }

}
