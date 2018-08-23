package com.yufan.library.pay;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.R;

import java.util.List;

/**
 * Created by zhanyangyang on 2018/8/23.
 */

public class PayWayAdapter extends RecyclerView.Adapter<PayWayAdapter.PayWayViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<String> datas;
    private Context mContext;

    public PayWayAdapter(OnItemClickListener onItemClickListener, List<String> datas, Context mContext) {
        this.onItemClickListener = onItemClickListener;
        this.datas = datas;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public PayWayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PayWayViewHolder viewHolder = new PayWayViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_pay_way_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PayWayViewHolder holder, final int position) {
        if (position == 0) {
            holder.icon_pay.setImageResource(R.drawable.icon_pay_ali);
            holder.tv_pay_title.setText("支付宝支付");
            holder.icon_pay_choice.setVisibility(View.VISIBLE);
        } else {
            holder.icon_pay.setImageResource(R.drawable.icon_pay_wechat);
            holder.tv_pay_title.setText("微信支付");
            holder.icon_pay_choice.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class PayWayViewHolder extends RecyclerView.ViewHolder {
        ImageView icon_pay;
        TextView tv_pay_title;
        ImageView icon_pay_choice;

        public PayWayViewHolder(View itemView) {
            super(itemView);
            icon_pay = itemView.findViewById(R.id.icon_pay);
            tv_pay_title = itemView.findViewById(R.id.tv_pay_title);
            icon_pay_choice = itemView.findViewById(R.id.icon_pay_choice);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}