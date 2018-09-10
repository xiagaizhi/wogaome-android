package com.yushi.leke.fragment.exhibition.fourpage;

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

public class doendBinder extends ItemViewBinder<doendinfo,doendBinder.ViewHolder> {
    ICallBack callBack;
    public doendBinder(ICallBack callBack) {
        this.callBack=callBack;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_doend_item,viewGroup,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final doendinfo doendinfo) {
        viewHolder.tv_vote_describe.setText(doendinfo.title);
        viewHolder.sdv.setImageURI(doendinfo.uri);
        viewHolder.tv_vote_describe.setClickable(true);
        viewHolder.tv_vote_describe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.OnBackResult(doendinfo);
            }
        });

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdv;
        public TextView tv_vote_checkall,tv_vote_title,tv_vote_sum,tv_vote_province,tv_vote_work,tv_vote_identity,tv_vote_name,tv_vote_describe;
        public Button btn_vote_support;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv=  itemView.findViewById(R.id.sdv);
            tv_vote_describe=itemView.findViewById(R.id.tv_vote_describe);
        }
    }
}
