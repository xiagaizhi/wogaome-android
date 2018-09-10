package com.yushi.leke.fragment.exhibition.fourpage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.exhibition.detail.ExhibitionDetailFragment;
import com.yushi.leke.fragment.exhibition.fourpage.allproject.allprojectBinder;
import com.yushi.leke.fragment.exhibition.fourpage.allproject.allprojectsFragment;
import com.yushi.leke.fragment.exhibition.fourpage.allproject.allprojectsinfo;
import com.yushi.leke.fragment.home.SubscriptionsFragment;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(fourpageVu.class)
public class fourpageFragment extends BaseListFragment<fourpageContract.IView> implements fourpageContract.Presenter {
    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(onunstartinfo1.class,new onunstartBinder1());
        adapter.register(onunstartinfo2.class,new onunstartBinder2());
        adapter.register(doendinfo.class,new doendBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {

            }
        }));
        adapter.register(allprojectsinfo.class,new allprojectBinder());
        vu.getRecyclerView().setAdapter(adapter);
        initinfo();
        list.add(new onunstartinfo1());
        initinfo1();
        list.add(new allprojectsinfo("http://oss.cyzone.cn/2018/0903/8a46c3cdd06dd931d05eebcdcb5ad8a9.png"));
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }
    private void initinfo1(){
        for (int i=0;i<10;i++){
            onunstartinfo2 info=new onunstartinfo2();
            list.add(info);
        }
    }
    private void initinfo(){
        for (int i=0;i<10;i++){
            doendinfo info=new doendinfo("这个来介绍项目的内容，并且字体不能超过24个字测试测试测试","http://oss.cyzone.cn/2018/0830/20180830040720965.jpg");
            list.add(info);
        }
    }
    @Override
    public void onLoadMore(int index) {

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void MyCallback() {
        getRootFragment().start(UIHelper.creat(allprojectsFragment.class).build());
    }
}
