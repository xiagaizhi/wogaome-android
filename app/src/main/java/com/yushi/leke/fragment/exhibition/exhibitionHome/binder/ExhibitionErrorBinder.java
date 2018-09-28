/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yushi.leke.fragment.exhibition.exhibitionHome.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.Global;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.Netutil;
import com.yushi.leke.App;
import com.yushi.leke.R;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionErrorInfo;

import me.drakeet.multitype.ItemViewBinder;


/**
 * 路演大厅
 */
public class ExhibitionErrorBinder extends ItemViewBinder<ExhibitionErrorInfo, ExhibitionErrorBinder.ViewHolder> {
    private ICallBack mICallBack;

    public ExhibitionErrorBinder(ICallBack iCallBack) {
        this.mICallBack = iCallBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.layout_error, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final ExhibitionErrorInfo category) {
        if (TextUtils.isEmpty(Netutil.GetNetworkType(App.getApp()))) {
            holder.tv_error_tips.setText("～呲～网络连接异常\\n请检查您的网络设置～呲～");
            holder.img_error.setImageResource(R.drawable.empty_nonetwork);
        } else {
            holder.tv_error_tips.setText("呲～呲～未发现任何数据");
            holder.img_error.setImageResource(R.drawable.empty_search_nodate);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mICallBack.OnBackResult();
                if (TextUtils.isEmpty(Netutil.GetNetworkType(App.getApp()))) {
                    holder.tv_error_tips.setText("～呲～网络连接异常\\n请检查您的网络设置～呲～");
                    holder.img_error.setImageResource(R.drawable.empty_nonetwork);
                } else {
                    holder.tv_error_tips.setText("呲～呲～未发现任何数据");
                    holder.img_error.setImageResource(R.drawable.empty_search_nodate);
                }
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_error_tips;
        private ImageView img_error;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_error_tips = itemView.findViewById(R.id.tv_error_tips);
            img_error = itemView.findViewById(R.id.img_error);
        }
    }


}
