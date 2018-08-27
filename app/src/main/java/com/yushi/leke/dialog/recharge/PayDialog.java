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
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.pay.PayWay;
import com.yushi.leke.R;
import com.yushi.leke.YFApi;

import org.json.JSONObject;

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
    private Button btn_pay;

    public PayDialog(@NonNull Context context, PayWayList payWayList, boolean isnormalPay) {
        super(context, R.style.dialog_common);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_pay, null);
        setContentView(rootView);
        this.mContext = context;
        this.mPayWayList = payWayList;

        id_payway = rootView.findViewById(R.id.id_payway);
        id_payway.setLayoutManager(new LinearLayoutManager(context));

        if (mPayWayList != null && mPayWayList.getPayApi() != null && mPayWayList.getPayApi().size() > 0) {
            selectPayWay = mPayWayList.getPayApi().get(0);
            selectPayWay.setSelect(true);
        }

        mAdapter = new PayWayAdapter(context, payways, isnormalPay, this);
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
        btn_pay = findViewById(R.id.btn_pay);
        if (isnormalPay) {
            btn_pay.setBackgroundResource(R.drawable.btn_bg_orange);
        } else {

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
                                boolean isHave = jsonObject.optBoolean("isHave");
                                if (isHave) {//拥有交易密码,验证交易密码
                                    SetRechargePwdDialog setRechargePwdDialog = new SetRechargePwdDialog(mContext,
                                            SetRechargePwdDialog.CHECK_RECHARGE_PWD, selectPayWay.getPayApiId(),
                                            mPayWayList.getGoodsName(), mPayWayList.getGoodsPrice(),
                                            mPayWayList.getGoodsPrice(), mPayWayList.getGoodsId());
                                    setRechargePwdDialog.show();
                                    dismiss();
                                } else {//没有交易密码
                                    // TODO: 2018/8/25 指引到交易密码设置界面
                                    dismiss();
                                    SetRechargePwdDialog setRechargePwdDialog = new SetRechargePwdDialog(mContext, SetRechargePwdDialog.SET_RECHARGE_PWD);
                                    setRechargePwdDialog.show();
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


}
