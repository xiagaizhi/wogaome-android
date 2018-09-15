package com.yushi.leke.fragment.exhibition.voteing.allproject;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        viewHolder.sdv.setImageURI(Allprojectsinfo.uri);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdv;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv=  itemView.findViewById(R.id.sdv);
        }
    }
}
