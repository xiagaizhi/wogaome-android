package com.yushi.leke.fragment.exhibition.mytestmvp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

public class unstartviewbinder1 extends ItemViewBinder<unstartinfo1,unstartviewbinder1.ViewHolder> {
    public unstartviewbinder1(){

    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_unstart_item1,viewGroup,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull unstartinfo1 unstartinfo1) {
        viewHolder.sdv1.setImageURI("http://oss.cyzone.cn/2018/0724/20180724094636938.png");
        viewHolder.sdv2.setImageURI("http://oss.cyzone.cn/2018/0830/20180830040720965.jpg");
        viewHolder.sdv3.setImageURI("http://oss.cyzone.cn/2018/0830/20180830040720965.jpg");
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdv1,sdv2,sdv3;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv1=itemView.findViewById(R.id.sdv1);
            sdv2=itemView.findViewById(R.id.sdv2);
            sdv3=itemView.findViewById(R.id.sdv3);
        }
    }
}
