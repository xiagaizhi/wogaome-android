package com.yushi.leke.fragment.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.bean.Person;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SubscriptionsVu.class)
public class SubscriptionsFragment extends BaseListFragment<SubscriptionsContract.IView> implements SubscriptionsContract.Presenter {

    private MultiTypeAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        adapter.register(SubscriptionBanner.class,new SubscriptionsBannerViewBinder());
        adapter.register(SubscriptionInfo.class,new SubscriptionsViewBinder());
        vu.getRecyclerView().setAdapter(adapter);
        list.add(new SubscriptionBanner());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        list.add(new SubscriptionInfo());
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onLoadMore(int index) {
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

                        getVu().getRecyclerView().getPTR().refreshComplete();
                        getVu(). getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NONE);
                    }
                });
    }


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

                        getVu().getRecyclerView().getPTR().refreshComplete();
                        getVu(). getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NONE);
                    }
                });

    }
}
