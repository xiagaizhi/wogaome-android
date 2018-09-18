package com.yushi.leke.fragment.album.detail;

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
@VuClass(DetailVu.class)
public class DetailFragment extends BaseListFragment<DetailContract.IView> implements DetailContract.Presenter {
    private MultiTypeAdapter adapter;
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
        adapter = new MultiTypeAdapter();
        adapter.register(Detailinfo.class,new DetailBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {

            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        list.add(new Detailinfo());
        list.add(new Detailinfo());
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }
}
