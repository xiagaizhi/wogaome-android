package com.yushi.leke.fragment.openTreasureBox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;
import com.yushi.leke.dialog.recharge.PayDialog;
import com.yushi.leke.fragment.browser.BrowserBaseFragment;

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
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).listTreatureBox())
                .useCache(true)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        String data = mApiBean.getData();
                        if (!TextUtils.isEmpty(data)) {
                            goodsInfoList = JSON.parseObject(data, GoodsInfoList.class);
                            if (goodsInfoList != null && goodsInfoList.getList() != null && goodsInfoList.getList().size() > 0) {
                                goodsInfos.clear();
                                goodsInfos.addAll(goodsInfoList.getList());
                                mGoodsInfo = goodsInfos.get(0);
                                mGoodsInfo.setSelected(true);
                                vu.getmRecyclerView().getAdapter().notifyDataSetChanged();
                            }
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

    /**
     * 消息
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Global.BROADCAST_PAY_RESUIL_ACTION:
                    Log.e("OpenTreasureBoxFragment", "1==" + System.currentTimeMillis());
                    showDialog(intent.getBooleanExtra(Global.INTENT_PAY_RESUIL_DATA, false));
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Global.BROADCAST_PAY_RESUIL_ACTION);
        LocalBroadcastManager.getInstance(_mActivity).registerReceiver(broadcastReceiver, intentFilter);
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
        if (mGoodsInfo == null) return;
        PayDialog payDialog = new PayDialog(_mActivity, mGoodsInfo.getGoodsId(), true);
        payDialog.show();
    }

    @Override
    public void openTreasureBoxDetail() {
        start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, ApiManager.getInstance().getApiConfig().getTreasureDetail()).build());

    }

    private void showDialog(boolean isSuccess) {
        if (isSuccess) {
            Bundle bundle = new Bundle();
            setFragmentResult(RESULT_OK, bundle);
            if (_mActivity == null || _mActivity.isFinishing()) return;
            new CommonDialog(_mActivity).setTitle("恭喜您，充值成功！")
                    .setPositiveName("确定")
                    .setHaveNegative(false)
                    .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                        @Override
                        public void onClick(CommonDialog commonDialog, int actionType) {
                            commonDialog.dismiss();
                            if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {
                                pop();
                            }
                        }
                    }).show();
        } else {
            if (_mActivity == null || _mActivity.isFinishing()) return;
            new CommonDialog(_mActivity).setTitle("本次充值失败，请重新充值！")
                    .setPositiveName("确定")
                    .setHaveNegative(false)
                    .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                        @Override
                        public void onClick(CommonDialog commonDialog, int actionType) {
                            commonDialog.dismiss();
                            if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {

                            }
                        }
                    }).show();
        }
    }
}
