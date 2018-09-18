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

package com.yushi.leke.fragment.exhibition.exhibitionHome;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.StringUtil;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;


/**
 * 路演大厅 路演item
 */
public class ExhibitionJustOneViewBinder extends ItemViewBinder<ExhibitionJustOneInfo, ExhibitionJustOneViewBinder.ViewHolder> {
    private ICallBack callBack;

    public ExhibitionJustOneViewBinder(ICallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_exhibition_justone, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ExhibitionJustOneInfo category) {
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
        holder.btn_apply.setOnClickListener(new View.OnClickListener() {
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
        public TextView tv_action_company;
        public TextView tv_action_time;
        public ImageView img_exhibition_bg;
        public Button btn_apply;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_action_company = itemView.findViewById(R.id.tv_action_company);
            tv_action_time = itemView.findViewById(R.id.tv_action_time);
            btn_apply = itemView.findViewById(R.id.btn_apply);
            img_exhibition_bg = itemView.findViewById(R.id.img_exhibition_bg);
        }
    }


}
