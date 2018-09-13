package com.yushi.leke.dialog.update;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yushi.leke.R;

import java.util.List;

/**
 * Created by zhanyangyang on 2018/8/23.
 */

public class UpdateInfoAdapter extends RecyclerView.Adapter<UpdateInfoAdapter.PayWayViewHolder> {
    private List<String> datas;
    private Context mContext;

    public UpdateInfoAdapter(Context mContext, List<String> datas) {
        this.datas = datas;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public PayWayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PayWayViewHolder viewHolder = new PayWayViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_update_info_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PayWayViewHolder holder, final int position) {
        holder.tv_info.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class PayWayViewHolder extends RecyclerView.ViewHolder {
        TextView tv_info;

        public PayWayViewHolder(View itemView) {
            super(itemView);
            tv_info = itemView.findViewById(R.id.tv_info);
        }
    }
}