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

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.StringUtil;
import com.yushi.leke.R;
import com.yushi.leke.fragment.exhibition.exhibitionHome.bean.ExhibitionInfo;

import me.drakeet.multitype.ItemViewBinder;


/**
 * 路演大厅 路演item
 */
public class ExhibitionViewBinder extends ItemViewBinder<ExhibitionInfo, ExhibitionViewBinder.ViewHolder> {
    private ICallBack callBack;

    public ExhibitionViewBinder(ICallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_exhibition, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ExhibitionInfo category) {
        switch (category.getActivityProgress()) {//活动进度（0--未开始，1--报名中，2--投票中，3--已结束）
            case 0:
                holder.tv_exhibtion_state.setText("未开始...");
                holder.tv_active_state.setVisibility(View.GONE);
                break;
            case 1:
                holder.tv_exhibtion_state.setText("报名中...");
                holder.tv_active_state.setText("立即报名");
                holder.tv_active_state.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.tv_exhibtion_state.setText("投票中...");
                holder.tv_active_state.setText("立即投票");
                holder.tv_active_state.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.tv_exhibtion_state.setText("已结束...");
                holder.tv_active_state.setVisibility(View.GONE);
                break;
        }

        holder.sdv.setImageURI(Uri.parse(category.getBgPicture()));
        holder.tv_title.setText(category.getTitle());
        holder.tv_action_company.setText("主办方：" + category.getOrganizer());
        holder.tv_action_time.setText("活动时间：" + StringUtil.formatTime(category.getStartDate(), category.getEndDate()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.OnBackResult(category);
                }
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdv;
        public TextView tv_title;
        public TextView tv_active_state;
        public TextView tv_action_company;
        public TextView tv_action_time;
        public TextView tv_exhibtion_state;
        public ImageView img_exhibition_bg;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_active_state = itemView.findViewById(R.id.tv_active_state);
            tv_action_company = itemView.findViewById(R.id.tv_action_company);
            tv_action_time = itemView.findViewById(R.id.tv_action_time);
            tv_exhibtion_state = itemView.findViewById(R.id.tv_exhibtion_state);
            img_exhibition_bg = itemView.findViewById(R.id.img_exhibition_bg);
        }
    }


}
