package com.yushi.leke.fragment.exhibition.fourpage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

public class onunstartBinder2 extends ItemViewBinder<onunstartinfo2,onunstartBinder2.ViewHolder> {
    public onunstartBinder2(){

    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_onunstart_item,viewGroup,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull onunstartinfo2 onunstartinfo2) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
