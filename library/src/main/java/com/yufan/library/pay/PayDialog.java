package com.yufan.library.pay;

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
import android.widget.TextView;


import com.alibaba.fastjson.JSON;
import com.yufan.library.R;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.api.remote.YFApi;
import com.yufan.library.pay.alipay.ToALiPay;
import com.yufan.library.pay.wenchatpay.WeChatPay;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 18/3/21.
 */

public class PayDialog extends Dialog implements PayWayAdapter.OnItemClickListener {
    private RecyclerView id_payway;
    private PayWayAdapter mAdapter;
    private List<PayWay> payways = new ArrayList<>();
    private PayWay selectPayWay;
    private PayWayList mPayWayList;
    private Context mContext;

    public PayDialog(@NonNull Context context, PayWayList payWayList) {
        super(context, R.style.dialog_common);
        this.mContext = context;
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_pay, null);
        setContentView(rootView);
        mPayWayList = payWayList;
        if (mPayWayList != null && mPayWayList.getPayApi() != null && mPayWayList.getPayApi().size() > 0) {
            selectPayWay = mPayWayList.getPayApi().get(0);
            selectPayWay.setSelect(true);
        }
        id_payway = rootView.findViewById(R.id.id_payway);
        id_payway.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new PayWayAdapter(context, payways, this);
        id_payway.setAdapter(mAdapter);
        rootView.findViewById(R.id.id_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        TextView tv_goods_info = (TextView) rootView.findViewById(R.id.tv_goods_info);
        tv_goods_info.setText("开宝箱");
        TextView tv_money = (TextView) rootView.findViewById(R.id.tv_order_money);
        tv_money.setText("¥" + mPayWayList.getGoodsPrice());
        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectPayWay != null) {
                    toPay(selectPayWay.getPayApiId());
                }
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

    private void toPay(final String payApiId) {
        String payMethod = "";
        if (TextUtils.equals("1", payApiId)) {
            payMethod = "aliPay";
        } else if (TextUtils.equals("2", payApiId)) {
            payMethod = "wxPay";
        }
        if (TextUtils.isEmpty(payMethod)) {
            return;
        }
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).toPay("v1", payMethod,
                mPayWayList.getGoodsName(), mPayWayList.getGoodsPrice(), mPayWayList.getGoodsPrice(),
                mPayWayList.getGoodsId(), selectPayWay.getPayApiId(), "2"))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if (TextUtils.equals("000", mApiBean.getCode())) {
                            String data = mApiBean.getData();
                            PayMetadata payMetadata = JSON.parseObject(data, PayMetadata.class);
                            if (TextUtils.equals("1", payApiId)) {
                                ToALiPay toALiPay = new ToALiPay();
                                toALiPay.action(mContext, payMetadata);
                            } else if (TextUtils.equals("2", payApiId)) {
                                WeChatPay.getInstance().toWeChatPay(mContext, payMetadata);
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
}
