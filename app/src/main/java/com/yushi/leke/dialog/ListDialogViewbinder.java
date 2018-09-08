package com.yushi.leke.dialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 作者：Created by zhanyangyang on 2018/9/8 14:13
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class ListDialogViewbinder extends ItemViewBinder<String, ListDialogViewbinder.ViewHolder> {
    private ICallBack mICallBack;

    public ListDialogViewbinder(ICallBack iCallBack) {
        this.mICallBack = iCallBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.dialog_list_item_layout, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final String s) {
        viewHolder.tv_name.setText(s);
        viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mICallBack != null) {
                    mICallBack.OnBackResult(s);
                }
            }
        });
        if (TextUtils.equals("女", s)) {
            viewHolder.view_line.setVisibility(View.GONE);
        } else {
            viewHolder.view_line.setVisibility(View.VISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ll_item;
        TextView tv_name;
        View view_line;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tv_name = itemView.findViewById(R.id.tv_name);
            view_line = itemView.findViewById(R.id.view_line);

        }
    }
}
