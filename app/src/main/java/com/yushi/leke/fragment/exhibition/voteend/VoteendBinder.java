package com.yushi.leke.fragment.exhibition.voteend;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

public class VoteendBinder extends ItemViewBinder<Voteendinfo, VoteendBinder.ViewHolder> {
    ICallBack callBack;

    public VoteendBinder(ICallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_doend_item, viewGroup, false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final Voteendinfo voteendinfo) {
        viewHolder.tv_vote_playsum.setText(String.valueOf(voteendinfo.getPlayCount()));
        viewHolder.sdv.setImageURI(voteendinfo.getVideo100Pic());
        viewHolder.tv_vote_title.setText(voteendinfo.getTitle());
        viewHolder.tv_vote_sum.setText(String.valueOf(voteendinfo.getVotes()));
        viewHolder.tv_vote_province.setText(voteendinfo.getAddress() + " / " + voteendinfo.getIndustry());
        viewHolder.tv_vote_name.setText("创业者：" + voteendinfo.getEntrepreneur());
        viewHolder.btn_vote_support.setText("已结束");
        viewHolder.btn_vote_support.setTextColor(Color.parseColor("#FF999999"));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.OnBackResult(voteendinfo.getAliVideoId(), voteendinfo.getTitle(), voteendinfo.getId());
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdv;
        public TextView tv_vote_playsum, tv_vote_title, tv_vote_sum, tv_vote_province, tv_vote_name;
        public Button btn_vote_support;
        public View rl_root;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
            tv_vote_playsum = itemView.findViewById(R.id.tv_vote_playsum);
            tv_vote_title = itemView.findViewById(R.id.tv_vote_title);
            tv_vote_sum = itemView.findViewById(R.id.tv_vote_sum);
            tv_vote_province = itemView.findViewById(R.id.tv_vote_province);
            tv_vote_name = itemView.findViewById(R.id.tv_vote_name);
            btn_vote_support = itemView.findViewById(R.id.btn_vote_support);
            rl_root = itemView.findViewById(R.id.rl_root);
        }
    }
}
