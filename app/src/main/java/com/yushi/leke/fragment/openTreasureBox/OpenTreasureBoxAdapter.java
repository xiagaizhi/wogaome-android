package com.yushi.leke.fragment.openTreasureBox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushi.leke.R;

import java.util.List;

/**
 * Created by test on 2018/8/21.
 */

public class OpenTreasureBoxAdapter extends RecyclerView.Adapter<OpenTreasureBoxAdapter.OpenTreasureBoxViewHolder> {
    private List<String> datas;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OpenTreasureBoxAdapter(List<String> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OpenTreasureBoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OpenTreasureBoxViewHolder viewHolder = new OpenTreasureBoxViewHolder(LayoutInflater.from(mContext).inflate(R.layout.open_treasure_box_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OpenTreasureBoxViewHolder holder, final int position) {
        holder.id_money.setText("" + datas.get(position));
        holder.id_treasure_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        if (position % 2 == 0){
            holder.id_treasure_box_bg.setSelected(true);
        }else {
            holder.id_treasure_box_bg.setSelected(false);
        }

    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class OpenTreasureBoxViewHolder extends RecyclerView.ViewHolder {
        View id_treasure_root;
        TextView id_money;
        TextView id_treasure_num;
        ImageView id_treasure_box_bg;


        public OpenTreasureBoxViewHolder(View itemView) {
            super(itemView);
            id_money = itemView.findViewById(R.id.id_money);
            id_treasure_num = itemView.findViewById(R.id.id_treasure_num);
            id_treasure_box_bg = itemView.findViewById(R.id.id_treasure_box_bg);
            id_treasure_root = itemView.findViewById(R.id.id_treasure_root);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
