package com.yushi.leke.fragment.openTreasureBox;

import android.graphics.Color;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.yufan.library.pay.PayCallbackInterf;
import com.yufan.library.util.ToastUtil;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.recharge.PayDialog;
import com.yushi.leke.fragment.bindPhone.BindPhoneFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(OpenTreasureBoxVu.class)
public class OpenTreasureBoxFragment extends BaseFragment<OpenTreasureBoxContract.IView> implements OpenTreasureBoxContract.Presenter, PayDialog.OpenBindPhoneInterf, PayCallbackInterf {
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
                                goodsInfos.clear();
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
        PayDialog payDialog = new PayDialog(_mActivity, mGoodsInfo.getGoodsId(), true, this);
        payDialog.setPayCallbackInterf(this);
        payDialog.show();
    }

    @Override
    public void openTreasureBoxDetail() {
        if (goodsInfoList != null && !TextUtils.isEmpty(goodsInfoList.getTreatureBoxDetailUrl())) {
            start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, goodsInfoList.getTreatureBoxDetailUrl()).build());
        }

    }

    @Override
    public void openBindPhone() {
        start(UIHelper.creat(BindPhoneFragment.class).build());
    }

    @Override
    public void onSuccess() {
        Bundle bundle = new Bundle();
        setFragmentResult(RESULT_OK, bundle);
        new MaterialDialog.Builder(_mActivity)
                .title("充值成功")
                .content("恭喜您，充值成功！")
                .positiveText("确定")
                .widgetColor(Color.BLUE)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which == DialogAction.POSITIVE) {
                            pop();
                        }
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onFail(String message) {
        new MaterialDialog.Builder(_mActivity)
                .title("充值失败")
                .content("本次充值失败，请重新充值！")
                .positiveText("确定")
                .widgetColor(Color.BLUE)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       dialog.dismiss();
                    }
                })
                .show();
    }
}
