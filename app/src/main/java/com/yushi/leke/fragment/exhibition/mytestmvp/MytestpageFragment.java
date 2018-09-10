package com.yushi.leke.fragment.exhibition.mytestmvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(MytestpageVu.class)
public class MytestpageFragment extends BaseListFragment<MytestpageContract.IView> implements MytestpageContract.Presenter {
    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(unstartinfo1.class,new unstartviewbinder1());
        adapter.register(unstartinfo2.class,new unstartviewbinder2());
        adapter.register(Mytestinfo.class,new MyviewBinder());
        vu.getRecyclerView().setAdapter(adapter);
        initinfo();
        list.add(new unstartinfo1());
        initinfo1();
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }
    private void initinfo1(){
        for (int i=0;i<10;i++){
            unstartinfo2 info=new unstartinfo2();
            list.add(info);
        }
    }
    private void initinfo(){
        for (int i=0;i<10;i++){
            Mytestinfo info=new Mytestinfo("这个来介绍项目的内容，并且字体不能超过24个字测试测试测试","http://oss.cyzone.cn/2018/0830/20180830040720965.jpg");
            list.add(info);
        }
    }
    @Override
    public void onLoadMore(int index) {

    }


    @Override
    public void onRefresh() {

    }
}
