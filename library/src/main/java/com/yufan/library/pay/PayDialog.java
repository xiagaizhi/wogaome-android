package com.yufan.library.pay;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.yufan.library.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 18/3/21.
 */

public class PayDialog extends Dialog implements PayWayAdapter.OnItemClickListener {
    private RecyclerView id_payway;
    private PayWayAdapter mAdapter;
    private List<String> payways = new ArrayList<>();

    public PayDialog(@NonNull Context context, String message, float money) {
        super(context, R.style.dialog_common);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_pay, null);
        setContentView(rootView);
        id_payway = rootView.findViewById(R.id.id_payway);
        payways.add("1");
        payways.add("2");
        id_payway.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new PayWayAdapter(this, payways, context);
        id_payway.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        rootView.findViewById(R.id.id_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        TextView tv_goods_info = (TextView) rootView.findViewById(R.id.tv_goods_info);
        tv_goods_info.setText(message);
        TextView tv_money = (TextView) rootView.findViewById(R.id.tv_order_money);
        tv_money.setText("¥" + money);
        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        window.setAttributes(p);
        window.setDimAmount(0.6f);
        window.setWindowAnimations(R.style.AnimBottomDialog);

    }

    @Override
    public void onItemClick(int position) {

    }
}
