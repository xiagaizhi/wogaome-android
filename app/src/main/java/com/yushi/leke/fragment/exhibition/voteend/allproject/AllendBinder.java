package com.yushi.leke.fragment.exhibition.voteend.allproject;

import android.annotation.SuppressLint;
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

public class AllendBinder extends ItemViewBinder<Allendinfo, AllendBinder.ViewHolder>{
    ICallBack callBack;
    public AllendBinder(ICallBack callBack){
        this.callBack = callBack;
    }
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_doend_item,viewGroup,false));
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final Allendinfo Allendinfo) {
        viewHolder.sdv.setImageURI(Allendinfo.getLogo());
        viewHolder.tv_vote_playsum.setText(String.valueOf(Allendinfo.getPlayCount()));
        viewHolder.tv_vote_title.setText(Allendinfo.getTitle());
        viewHolder.tv_vote_sum.setText(String.valueOf(Allendinfo.getVotes()));
        viewHolder.tv_vote_province.setText(Allendinfo.getAddress()+" / "+ Allendinfo.getIndustry());
        viewHolder.tv_vote_name.setText("创业者："+ Allendinfo.getEntrepreneur());
        viewHolder.tv_vote_describe.setText(Allendinfo.getDesc());
        viewHolder.btn_vote_support.setText("已结束");
        viewHolder.btn_vote_support.setTextColor(R.color.color_gray_levele);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public View rl_root;
        public SimpleDraweeView sdv;
        public TextView tv_vote_playsum,tv_vote_title,tv_vote_sum
                ,tv_vote_province,tv_vote_name,tv_vote_describe;
        public Button btn_vote_support;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv=  itemView.findViewById(R.id.sdv);
            tv_vote_playsum=itemView.findViewById(R.id.tv_vote_playsum);
            tv_vote_title=itemView.findViewById(R.id.tv_vote_title);
            tv_vote_sum=itemView.findViewById(R.id.tv_vote_sum);
            tv_vote_province=itemView.findViewById(R.id.tv_vote_province);
            tv_vote_name=itemView.findViewById(R.id.tv_vote_name);
            btn_vote_support=itemView.findViewById(R.id.btn_vote_support);
            tv_vote_describe=itemView.findViewById(R.id.tv_vote_describe);
            rl_root = itemView.findViewById(R.id.rl_root);
        }
    }
}
