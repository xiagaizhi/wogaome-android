package com.yushi.leke.fragment.exhibition.mytestmvp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

public class unstartviewbinder2 extends ItemViewBinder<unstartinfo2,unstartviewbinder2.ViewHolder> {
    public unstartviewbinder2(){

    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_unstart_item,viewGroup,false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull unstartinfo2 unstartinfo2) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
