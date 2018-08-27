package com.yushi.leke.fragment.openTreasureBox;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.yufan.library.browser.BrowserBaseFragment;
import com.yufan.library.inject.VuClass;
import com.yufan.library.util.ToastUtil;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.recharge.PayDialog;
import com.yushi.leke.dialog.recharge.PayWayList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(OpenTreasureBoxVu.class)
public class OpenTreasureBoxFragment extends BaseFragment<OpenTreasureBoxContract.IView> implements OpenTreasureBoxContract.Presenter {
    private List<GoodsInfo> goodsInfos = new ArrayList<>();
    private GoodsInfo mGoodsInfo;
    private GoodsInfoList goodsInfoList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).listTreatureBox("v1", "1"))
                .useCache(true)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            String data = mApiBean.getData();
                            goodsInfoList = JSON.parseObject(data, GoodsInfoList.class);
                            if (goodsInfoList != null && goodsInfoList.getGoodsInfo() != null && goodsInfoList.getGoodsInfo().size() > 0) {
                                goodsInfos.addAll(goodsInfoList.getGoodsInfo());
                                mGoodsInfo = goodsInfos.get(0);
                                mGoodsInfo.setSelected(true);
                                vu.getmRecyclerView().getAdapter().notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.normal(_mActivity, mApiBean.getMessage(), Toast.LENGTH_SHORT).show();
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


    @Override
    public void onRefresh() {

    }

    @Override
    public List<GoodsInfo> getDatas() {
        return goodsInfos;
    }

    @Override
    public void selectGoodInfo(GoodsInfo goodsInfo) {
        this.mGoodsInfo = goodsInfo;
        vu.getmRecyclerView().getAdapter().notifyDataSetChanged();
    }

    @Override
    public void toPay() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).tradeMethod("v1", 0, 1, mGoodsInfo.getGoodsId()))
                .useCache(true)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            String data = mApiBean.getData();
                            PayWayList payWayList = JSON.parseObject(data, PayWayList.class);
                            PayDialog payDialog = new PayDialog(_mActivity, payWayList,true);
                            payDialog.show();
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

    @Override
    public void openTreasureBoxDetail() {
        if (goodsInfoList != null && !TextUtils.isEmpty(goodsInfoList.getTreatureBoxDetailUrl())) {
            start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, goodsInfoList.getTreatureBoxDetailUrl()).build());
        }
    }
}
