package com.yushi.leke.fragment.exhibition.vote;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushi.leke.R;

import java.util.List;

/**
 * 作者：Created by zhanyangyang on 2018/9/10 19:58
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class WinListAdapter extends RecyclerView.Adapter<WinListAdapter.ItemViewHolder> {
    private List<WinListInfo> winListInfoList;

    public WinListAdapter(List<WinListInfo> winListInfoList) {
        this.winListInfoList = winListInfoList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_win_big_item_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_win_normal_item_layout, viewGroup, false);
        }
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 3) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (position<3){

        }else {

        }
    }

    @Override
    public int getItemCount() {
        return winListInfoList == null ? 0 : winListInfoList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_rank;
        ImageView img_rank;
        SimpleDraweeView img_logo;
        TextView tv_title;
        TextView tv_area_industry;
        TextView tv_username;
        TextView tv_ticket;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_rank = itemView.findViewById(R.id.tv_rank);
            img_rank = itemView.findViewById(R.id.img_rank);
            img_logo = itemView.findViewById(R.id.img_logo);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_area_industry = itemView.findViewById(R.id.tv_area_industry);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_ticket = itemView.findViewById(R.id.tv_ticket);
        }
    }
}
