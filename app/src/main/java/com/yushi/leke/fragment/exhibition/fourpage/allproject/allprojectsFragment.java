package com.yushi.leke.fragment.exhibition.fourpage.allproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;

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
        for (int i=0;i<10;i++){
            list.add(info);
        }
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }
}
