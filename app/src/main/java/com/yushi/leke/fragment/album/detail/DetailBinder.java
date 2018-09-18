package com.yushi.leke.fragment.album.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

public class DetailBinder extends ItemViewBinder<Detailinfo, DetailBinder.ViewHolder> {
    private ICallBack callBack;
    public DetailBinder(ICallBack callBack){
        this.callBack=callBack;
    }
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        return new ViewHolder(layoutInflater.inflate(R.layout.xx_introduction_item, viewGroup, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull Detailinfo detailinfo) {
        viewHolder.tv_title.setText(detailinfo.getTitle());
        viewHolder.tv_content.setText(detailinfo.getContent());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_content;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_introduction_title);
            tv_content=itemView.findViewById(R.id.tv_introduction_content);
        }
    }
}
