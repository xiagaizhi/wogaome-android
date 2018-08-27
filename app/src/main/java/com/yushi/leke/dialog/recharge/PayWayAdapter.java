package com.yushi.leke.dialog.recharge;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.pay.PayWay;
import com.yushi.leke.R;

import java.util.List;

/**
 * Created by zhanyangyang on 2018/8/23.
 */

public class PayWayAdapter extends RecyclerView.Adapter<PayWayAdapter.PayWayViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<PayWay> datas;
    private Context mContext;
    private int selectPosition;
    private boolean isnormalPay;

    public PayWayAdapter(Context mContext, List<PayWay> datas, boolean isnormalPay, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.datas = datas;
        this.mContext = mContext;
        this.isnormalPay = isnormalPay;
    }


    @NonNull
    @Override
    public PayWayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PayWayViewHolder viewHolder = new PayWayViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_pay_way_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PayWayViewHolder holder, final int position) {
        final PayWay payWay = datas.get(position);
        if (TextUtils.equals(payWay.getPayApiId(), "1")) {//支付宝
            holder.icon_pay.setImageResource(R.drawable.ic_pay_ali);
            holder.tv_pay_title.setText("支付宝支付");
        } else if (TextUtils.equals(payWay.getPayApiId(), "2")) {//微信
            holder.icon_pay.setImageResource(R.drawable.ic_pay_wechat);
            holder.tv_pay_title.setText("微信支付");
        }
        if (isnormalPay) {
            holder.icon_pay_choice.setImageResource(R.drawable.ic_pay_choice);
        } else {
            holder.icon_pay_choice.setImageResource(R.drawable.ic_pay_choice_metal);
        }
        if (payWay.isSelect()) {
            selectPosition = position;
            holder.icon_pay_choice.setVisibility(View.VISIBLE);
        } else {
            holder.icon_pay_choice.setVisibility(View.GONE);
        }
        holder.id_payway_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position != selectPosition) {
                    if (onItemClickListener != null) {
                        datas.get(selectPosition).setSelect(false);
                        payWay.setSelect(true);
                        onItemClickListener.onItemClick(payWay);
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class PayWayViewHolder extends RecyclerView.ViewHolder {
        ImageView icon_pay;
        TextView tv_pay_title;
        ImageView icon_pay_choice;
        View id_payway_root;

        public PayWayViewHolder(View itemView) {
            super(itemView);
            icon_pay = itemView.findViewById(R.id.icon_pay);
            tv_pay_title = itemView.findViewById(R.id.tv_pay_title);
            icon_pay_choice = itemView.findViewById(R.id.icon_pay_choice);
            id_payway_root = itemView.findViewById(R.id.id_payway_root);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PayWay payWay);
    }
}