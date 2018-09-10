package com.yushi.leke.fragment.exhibition.mytestmvp;

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

public class MyviewBinder extends ItemViewBinder<Mytestinfo,MyviewBinder.ViewHolder> {
    public MyviewBinder() {

    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_mytestview,viewGroup,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull Mytestinfo mytestinfo) {
        viewHolder.tv_vote_describe.setText(mytestinfo.title);
        viewHolder.sdv.setImageURI(mytestinfo.uri);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdv;
        public TextView tv_vote_title,tv_vote_sum,tv_vote_province,tv_vote_work,tv_vote_identity,tv_vote_name,tv_vote_describe;
        public Button btn_vote_support;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv=  itemView.findViewById(R.id.sdv);
            tv_vote_describe=itemView.findViewById(R.id.tv_vote_describe);
        }
    }
}
