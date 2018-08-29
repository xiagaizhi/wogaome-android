package com.yushi.leke.dialog.recharge;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.pay.PayWay;
import com.yushi.leke.R;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 18/3/21.
 */

public class PayDialog extends Dialog implements PayWayAdapter.OnItemClickListener {
    private RecyclerView id_payway;
    private PayWayAdapter mAdapter;
    private PayWay selectPayWay;
    private Context mContext;
    private Button btn_pay;
    private List<PayWay> payways = new ArrayList<>();
    private TextView tv_money;
    private TextView tv_goods_info;
    private PayWayList mPayWayList;
    private OpenBindPhoneInterf openBindPhoneInterf;

    public PayDialog(@NonNull Context context, String goodsId, boolean isnormalPay, OpenBindPhoneInterf openBindPhoneInterf) {
        super(context, R.style.dialog_common);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_pay, null);
        setContentView(rootView);
        this.mContext = context;
        this.openBindPhoneInterf = openBindPhoneInterf;
        id_payway = rootView.findViewById(R.id.id_payway);
        tv_goods_info = (TextView) rootView.findViewById(R.id.tv_goods_info);
        tv_money = (TextView) rootView.findViewById(R.id.tv_order_money);
        id_payway.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new PayWayAdapter(context, payways, isnormalPay, this);
        id_payway.setAdapter(mAdapter);
        btn_pay = findViewById(R.id.btn_pay);
        getPayWays(goodsId);
        rootView.findViewById(R.id.id_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if (isnormalPay) {
            btn_pay.setBackgroundResource(R.drawable.btn_bg_orange);
        } else {
            btn_pay.setBackgroundResource(R.drawable.btn_bg_golden_deep);
        }
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHavePayPwd();
            }
        });
        rootView.findViewById(R.id.id_top_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCanceledOnTouchOutside(false);
    }


    private void getPayWays(String goodsId) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).tradeMethod("v1", 0, 1, goodsId))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                            String data = mApiBean.getData();
                            mPayWayList = JSON.parseObject(data, PayWayList.class);
                            if (mPayWayList != null) {
                                tv_money.setText("¥" + mPayWayList.getGoodsPrice());
                                tv_goods_info.setText("" + mPayWayList.getGoodsName());
                                if (mPayWayList.getPayApi() != null && mPayWayList.getPayApi().size() > 0) {
                                    selectPayWay = mPayWayList.getPayApi().get(0);
                                    selectPayWay.setSelect(true);
                                    payways.addAll(mPayWayList.getPayApi());
                                    mAdapter.notifyDataSetChanged();
                                }
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

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        window.setAttributes(p);
        window.setDimAmount(0.6f);
        window.setWindowAnimations(R.style.AnimBottomDialog);
    }

    @Override
    public void onItemClick(PayWay payWay) {
        this.selectPayWay = payWay;
        id_payway.getAdapter().notifyDataSetChanged();
    }


    private void checkHavePayPwd() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).haveTradePwd("v1", "9999"))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        try {
                            if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                                String data = mApiBean.getData();
                                JSONObject jsonObject = new JSONObject(data);
                                int isHave = jsonObject.optInt("isHave");
                                String phoneNumber = jsonObject.optString("phoneNumber");
                                if (!TextUtils.isEmpty(phoneNumber)) {//绑定手机
                                    dismiss();
                                    SetRechargePwdDialog setRechargePwdDialog;
                                    if (isHave == 1) {//拥有交易密码,验证交易密码
                                        setRechargePwdDialog = new SetRechargePwdDialog(mContext,
                                                SetRechargePwdDialog.CHECK_RECHARGE_PWD, selectPayWay.getPayApiId(),
                                                mPayWayList.getGoodsName(), mPayWayList.getGoodsPrice(),
                                                mPayWayList.getGoodsPrice(), mPayWayList.getGoodsId());
                                    } else {//没有交易密码
                                        setRechargePwdDialog = new SetRechargePwdDialog(mContext, SetRechargePwdDialog.SET_RECHARGE_PWD);
                                    }
                                    if (setRechargePwdDialog != null) {
                                        setRechargePwdDialog.show();
                                    }
                                } else {//未绑定过手机
                                    new CommonDialog(getContext()).setTitle("您尚未绑定手机，请先绑定安全手机！")
                                            .setNegativeName("取消")
                                            .setPositiveName("去绑定")
                                            .setHaveNegative(true)
                                            .setCommonDialogCanceledOnTouchOutside2(false)
                                            .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                                                @Override
                                                public void onClick(CommonDialog commonDialog, int actionType) {
                                                    commonDialog.dismiss();
                                                    if (actionType == CommonDialog.COMMONDIALOG_ACTION_POSITIVE) {
                                                        dismiss();
                                                        if (openBindPhoneInterf != null) {
                                                            openBindPhoneInterf.openBindPhone();
                                                        }
                                                    }
                                                }
                                            }).show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
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

    public interface OpenBindPhoneInterf {
        void openBindPhone();
    }

}
