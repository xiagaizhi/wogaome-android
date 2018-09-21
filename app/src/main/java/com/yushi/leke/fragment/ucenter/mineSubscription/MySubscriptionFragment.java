package com.yushi.leke.fragment.ucenter.mineSubscription;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.YFListHttpCallBack;
import com.yufan.library.base.BaseListFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.view.recycler.PageInfo;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;
import com.yushi.leke.fragment.album.AlbumDetailFragment;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zhanyangyang on 18/8/2.
 */
@VuClass(MySubscriptionVu.class)
public class MySubscriptionFragment extends BaseListFragment<MySubscriptionContract.IView> implements MySubscriptionContract.Presenter {
    private MultiTypeAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new MultiTypeAdapter();
        adapter.register(MySubscriptionInfo.class, new MySubscriptionViewBinder(new ICallBack() {
            @Override
            public void OnBackResult(Object... s) {
                final MySubscriptionInfo mySubscriptionInfo = (MySubscriptionInfo) s[1];
                switch ((int) s[0]) {
                    case 1://跳转详情
                        start(UIHelper.creat(AlbumDetailFragment.class).put(Global.BUNDLE_KEY_ALBUMID, mySubscriptionInfo.getAlbumId()).build());
                        break;
                    case 2://取消订阅
                        new CommonDialog(_mActivity).setTitle("取消订阅")
                                .setNegativeName("取消").setPositiveName("确定").setCommonClickListener(new CommonDialog.CommonDialogClick() {
                            @Override
                            public void onClick(CommonDialog commonDialog, int actionType) {
                                if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {
                                    unregisteralbum(mySubscriptionInfo.getAlbumId());
                                    list.remove(mySubscriptionInfo);
                                    getVu().getRecyclerView().getAdapter().notifyDataSetChanged();
                                }
                                commonDialog.dismiss();
                            }
                        }).show();
                        break;
                }

            }
        }));
        adapter.setItems(list);
        getVu().getRecyclerView().setAdapter(adapter);
        onRefresh();
    }

    private void unregisteralbum(String albumId) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).unregisteralbum(albumId)).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {

            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public void onLoadMore(int index) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getMySubscribe(index))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        MySubscriptionInfoList mySubscriptionInfos = JSON.parseObject(mApiBean.getData(), MySubscriptionInfoList.class);
                        if (mySubscriptionInfos != null && mySubscriptionInfos.getList().size() > 0) {
                            list.addAll(mySubscriptionInfos.getList());
                            vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                        } else {
                            vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                        }
                    }
                });
    }


    @Override
    public void onRefresh() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getMySubscribe(1))
                .useCache(false)
                .enqueue(new YFListHttpCallBack(getVu()) {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        super.onSuccess(mApiBean);
                        MySubscriptionInfoList mySubscriptionInfos = JSON.parseObject(mApiBean.getData(), MySubscriptionInfoList.class);
                        if (mySubscriptionInfos != null && mySubscriptionInfos.getList().size() > 0) {
                            list.clear();
                            list.addAll(mySubscriptionInfos.getList());
                            vu.getRecyclerView().getAdapter().notifyDataSetChanged();
                        } else {
                            vu.setStateEmpty();
                            vu.getRecyclerView().getPageManager().setPageState(PageInfo.PAGE_STATE_NO_MORE);
                        }
                    }
                });
    }
}
