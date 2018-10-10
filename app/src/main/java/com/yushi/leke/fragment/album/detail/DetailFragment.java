package com.yushi.leke.fragment.album.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.fragment.album.AlbumDetailFragment;
import com.yushi.leke.fragment.album.AlbumDetailinfo;
import com.yushi.leke.fragment.album.Introduction;
import com.yushi.leke.fragment.exhibition.voteing.VoteingFragment;
import com.yushi.leke.util.ArgsUtil;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(DetailVu.class)
public class DetailFragment extends BaseListFragment<DetailContract.IView> implements DetailContract.Presenter,ICallBack {
    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AlbumDetailFragment albumDetailFragment= (AlbumDetailFragment) getParentFragment();
        albumDetailFragment.setmICallBack(this);
        init();
    }
    void init(){
        adapter = new MultiTypeAdapter();
        adapter.register(Introduction.class,new DetailBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {

            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void OnBackResult(Object... s) {
        AlbumDetailinfo infolist= (AlbumDetailinfo) s[0];
        list.clear();
        list.addAll(infolist.getAlbumDetailInfo().getIntroduction());
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }
}
