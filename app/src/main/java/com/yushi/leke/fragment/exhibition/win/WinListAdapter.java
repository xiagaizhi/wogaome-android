package com.yushi.leke.fragment.exhibition.win;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private Context mContext;
    private List<WinProjectInfo> winListInfoList;

    public WinListAdapter(Context context, List<WinProjectInfo> winListInfoList) {
        this.mContext = context;
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
        if (position == 0) {
            holder.tv_rank.setTextColor(mContext.getResources().getColor(R.color.color_FA5A5A));
            holder.view_line_top.setVisibility(View.VISIBLE);
            holder.img_rank.setImageResource(R.drawable.ic_win_rank1);
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_vote_red);
            holder.tv_ticket.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            holder.tv_ticket.setTextColor(mContext.getResources().getColor(R.color.color_FA5A5A));
        } else if (position == 1) {
            holder.tv_rank.setTextColor(mContext.getResources().getColor(R.color.color_yellow_level0));
            holder.view_line_top.setVisibility(View.GONE);
            holder.img_rank.setImageResource(R.drawable.ic_win_rank2);
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_vote_yellow);
            holder.tv_ticket.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            holder.tv_ticket.setTextColor(mContext.getResources().getColor(R.color.color_yellow_level0));
        } else if (position == 2) {
            holder.tv_rank.setTextColor(mContext.getResources().getColor(R.color.color_blue_level6));
            holder.view_line_top.setVisibility(View.GONE);
            holder.img_rank.setImageResource(R.drawable.ic_win_rank3);
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_vote_blue);
            holder.tv_ticket.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
            holder.tv_ticket.setTextColor(mContext.getResources().getColor(R.color.color_blue_level6));
        }
        holder.tv_rank.setText(String.valueOf(position + 1));
        holder.img_logo.setImageURI("https://lelian-dev.oss-cn-shanghai.aliyuncs.com/avatar/2018-09-07/96c8cceb-3671-42dc-904c-86e2e9ddeb70.jpg");
        holder.tv_title.setText("标题");
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
        View view_line_top;
        View view_line_bottom;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_rank = itemView.findViewById(R.id.tv_rank);
            img_rank = itemView.findViewById(R.id.img_rank);
            img_logo = itemView.findViewById(R.id.img_logo);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_area_industry = itemView.findViewById(R.id.tv_area_industry);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_ticket = itemView.findViewById(R.id.tv_ticket);
            view_line_top= itemView.findViewById(R.id.view_line_top);
            view_line_bottom= itemView.findViewById(R.id.view_line_bottom);
        }
    }
}
