package com.yushi.leke.fragment.exhibition.voteing;

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

public class VoteingBinder extends ItemViewBinder<Voteinginfo,VoteingBinder.ViewHolder> {
    ICallBack callBack;
    public VoteingBinder(ICallBack callBack) {
        this.callBack=callBack;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_doend_item,viewGroup,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final Voteinginfo Voteinginfo) {
        viewHolder.tv_vote_playsum.setText(String.valueOf(Voteinginfo.getPlayCount()));
        viewHolder.sdv.setImageURI(Voteinginfo.getVideo100Pic());
        viewHolder.tv_vote_title.setText(Voteinginfo.getTitle());
        viewHolder.tv_vote_sum.setText(String.valueOf(Voteinginfo.getVotes()));
        viewHolder.tv_vote_province.setText(Voteinginfo.getAddress()+" / "+Voteinginfo.getIndustry());
        viewHolder.tv_vote_name.setText("创业者："+Voteinginfo.getEntrepreneur());
        viewHolder.tv_vote_describe.setText(Voteinginfo.getDesc());
        viewHolder.tv_vote_describe.setClickable(true);
        viewHolder.tv_vote_describe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnBackResult(Voteinginfo);
            }
        });

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
