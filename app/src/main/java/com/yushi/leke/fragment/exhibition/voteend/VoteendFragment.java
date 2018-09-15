package com.yushi.leke.fragment.exhibition.voteend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.YFApi;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(VoteendVu.class)
public class VoteendFragment extends BaseListFragment<VoteendContract.IView> implements VoteendContract.Presenter {
    private MultiTypeAdapter adapter;
    private Voteendinfolist doendinfo;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter=new MultiTypeAdapter();
        Log.d("LOG","oncreat");
        adapter.register(Voteendinfo.class,new VoteendBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {

            }
        }));
        vu.getRecyclerView().setAdapter(adapter);
        Loadmore();
        adapter.setItems(list);
        vu.getRecyclerView().getAdapter().notifyDataSetChanged();
    }
    @Override
    public void onLoadMore(int index) {

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void MyCallback() {

    }

    @Override
    public void Loadmore() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getvoteenddata(getVu().getRecyclerView().getPageManager().getCurrentIndex(),"1")).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    doendinfo= JSON.parseObject(mApiBean.getData(), Voteendinfolist.class);
                    list.addAll(doendinfo.getProjectList());
                    getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
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
}
