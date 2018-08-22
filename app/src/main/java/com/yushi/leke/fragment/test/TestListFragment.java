package com.yushi.leke.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.view.recycler.PageInfo;
import com.yufan.share.ShareModel;
import com.yushi.leke.UIHelper;
import com.yushi.leke.activity.AliyunPlayerActivity;
import com.yushi.leke.bean.Person;
import com.yushi.leke.fragment.main.MainFragment;
import com.yushi.leke.fragment.wallet.MyWalletFragment;
import com.yushi.leke.uamp.ui.MediaBrowserFragment;
import com.yushi.leke.share.ShareMenuActivity;

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
        ((MainFragment)getParentFragment()).startBrotherFragment(UIHelper.creat(MediaBrowserFragment.class).put(MediaBrowserFragment.ARG_MEDIA_ID,"meng").build());
    }

    @Override
    public void startPlayer() {
        Intent intent=new Intent();
        intent.setClass(getContext(),AliyunPlayerActivity.class);
        startActivity(intent);
    }

    @Override
    public void share() {
        ShareModel shareModel=  new ShareModel();
        shareModel.setContent("内容");
        shareModel.setTargetUrl("http://www.baidu.com");
        shareModel.setTitle("title");
        shareModel.setIcon("https://gitbook.cn/gitchat/author/5a002a147393bc6262dfb1c2");
        ShareMenuActivity.startShare(this,shareModel);
    }

    @Override
    public void openMyWallet() {
        ((MainFragment)getParentFragment()).startBrotherFragment(UIHelper.creat(MyWalletFragment.class).build());
    }
}
