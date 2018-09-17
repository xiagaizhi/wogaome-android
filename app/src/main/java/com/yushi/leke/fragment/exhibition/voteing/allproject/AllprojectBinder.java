package com.yushi.leke.fragment.exhibition.voteing.allproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

public class AllprojectBinder extends ItemViewBinder<Allprojectsinfo,AllprojectBinder.ViewHolder>{
    public AllprojectBinder(){

    }
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_doend_item,viewGroup,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final Allprojectsinfo Allprojectsinfo) {
        viewHolder.sdv.setImageURI(Allprojectsinfo.getLogo());
        viewHolder.tv_vote_playsum.setText(String.valueOf(Allprojectsinfo.getPlayCount()));
        viewHolder.tv_vote_title.setText(Allprojectsinfo.getTitle());
        viewHolder.tv_vote_sum.setText(String.valueOf(Allprojectsinfo.getVotes()));
        viewHolder.tv_vote_province.setText(Allprojectsinfo.getAddress()+" / "+Allprojectsinfo.getIndustry());
        viewHolder.tv_vote_name.setText("创业者："+Allprojectsinfo.getEntrepreneur());
        viewHolder.tv_vote_describe.setText(Allprojectsinfo.getDesc());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
