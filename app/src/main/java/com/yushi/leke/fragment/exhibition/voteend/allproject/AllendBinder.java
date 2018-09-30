package com.yushi.leke.fragment.exhibition.voteend.allproject;

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
import com.yushi.leke.fragment.exhibition.voteing.allproject.Allprojectsinfo;
import com.yushi.leke.util.FormatImageUtil;

import me.drakeet.multitype.ItemViewBinder;

public class AllendBinder extends ItemViewBinder<Allprojectsinfo, AllendBinder.ViewHolder>{
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
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final Allprojectsinfo allendinfo) {
        viewHolder.sdv.setImageURI(FormatImageUtil.converImageUrl(allendinfo.getVideo100Pic(),256,144));
        viewHolder.tv_vote_playsum.setText(String.valueOf(allendinfo.getPlayCount()));
        viewHolder.tv_vote_title.setText(allendinfo.getTitle());
        viewHolder.tv_vote_sum.setText(String.valueOf(allendinfo.getVotes()));
        viewHolder.tv_vote_province.setText(allendinfo.getAddress()+" / "+ allendinfo.getIndustry());
        viewHolder.tv_vote_name.setText("创业者："+ allendinfo.getEntrepreneur());
        viewHolder.btn_vote_support.setText("已结束");
        viewHolder.btn_vote_support.setTextColor(Color.parseColor("#FF999999"));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public View rl_root;
        public SimpleDraweeView sdv;
        public TextView tv_vote_playsum,tv_vote_title,tv_vote_sum
                ,tv_vote_province,tv_vote_name;
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
            rl_root = itemView.findViewById(R.id.rl_root);
        }
    }
}
