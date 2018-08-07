package com.yushi.leke.plugin.operation.dbtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.plugin.bean.Person;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by mengfantao on 18/7/2.
 */
@VuClass(TestVu.class)
public class TestListFragment extends BaseListFragment<DbTestContract.View> implements DbTestContract.Presenter {

    private  MultiTypeAdapter adapter;
    @Override
    public void onRefresh() {
        Observable.create(new ObservableOnSubscribe<List<Person>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Person>> e) throws Exception {

                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Person>>() {
                    @Override
                    public void accept(@NonNull List<Person> persons) throws Exception {
                        setAdapterData(persons);
                        getVu().getRecyclerView().getPTR().refreshComplete();
                        getVu(). getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NONE);
                    }
                });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         adapter=new MultiTypeAdapter();
        adapter.register(Person.class,new CategoryItemViewBinder());
        adapter.setItems(list);
        vu.getRecyclerView().setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLoadMore(int index) {
        onRefresh();
    }




    @Override
    public void batchinsert() {

    }

    @Override
    public void startPlayer() {

    }

    @Override
    public void startMedia() {

    }
}
