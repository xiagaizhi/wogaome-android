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

package com.yushi.leke.fragment.home.binder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;
import com.yushi.leke.fragment.home.bean.Homeinfo;
import com.yushi.leke.util.FormatImageUtil;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 订阅专栏 订阅 专辑
 */
public class SubscriptionsViewBinder extends ItemViewBinder<Homeinfo, SubscriptionsViewBinder.ViewHolder> {
    private  ICallBack callBack;
    public SubscriptionsViewBinder(ICallBack callBack) {
this.callBack=callBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_subscriptions, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Homeinfo category) {
        holder.tv_playcount.setText(category.getViewTimes());
        holder.tv_title.setText(category.getAlbumName());
        holder.tv_name.setText(category.getCreator());
        holder.tv_industry.setText(category.getCreatorInfo());
        holder.tv_othertitle.setText(category.getIntro());

        category.getTags();
        if (!TextUtils.isEmpty(category.getTags())) {
            String[] tags = category.getTags().split(",");
            if (tags.length == 0) {
                holder.ll_tag.setVisibility(View.GONE);
            } else if (tags.length == 1) {
                holder.ll_tag.setVisibility(View.VISIBLE);
                holder.tv_tab1.setText(tags[0]);
                holder.tv_tab1.setVisibility(View.VISIBLE);
                holder.tv_tab2.setVisibility(View.GONE);
                holder.tv_tab3.setVisibility(View.GONE);
            } else if (tags.length == 2) {
                holder.ll_tag.setVisibility(View.VISIBLE);
                holder.tv_tab1.setText(tags[0]);
                holder.tv_tab2.setText(tags[1]);
                holder.tv_tab1.setVisibility(View.VISIBLE);
                holder.tv_tab2.setVisibility(View.VISIBLE);
                holder.tv_tab3.setVisibility(View.GONE);
            } else if (tags.length == 3) {
                holder.ll_tag.setVisibility(View.VISIBLE);
                holder.tv_tab1.setText(tags[0]);
                holder.tv_tab2.setText(tags[1]);
                holder.tv_tab3.setText(tags[2]);
                holder.tv_tab1.setVisibility(View.VISIBLE);
                holder.tv_tab2.setVisibility(View.VISIBLE);
                holder.tv_tab3.setVisibility(View.VISIBLE);
            }
        } else {
            holder.ll_tag.setVisibility(View.GONE);
        }

        holder.sdv.setImageURI(FormatImageUtil.converImageUrl(category.getHorizontalIcon(),218,168));
        if (getAdapter().getItemCount()==getPosition(holder)-1) {
            holder.view_bottom_line.setVisibility(View.INVISIBLE);
        } else {
            holder.view_bottom_line.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack!=null){
                    callBack.OnBackResult(category);
                }
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdv;
        View view_bottom_line;
        TextView tv_playcount,tv_title,tv_ab1,tv_ab2,tv_name,tv_industry,tv_othertitle;
        TextView tv_tab1,tv_tab2,tv_tab3;
        LinearLayout ll_tag;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv=itemView.findViewById(R.id.sdv);
            view_bottom_line=itemView.findViewById(R.id.view_bottom_line);
            tv_playcount=itemView.findViewById(R.id.tv_playcount);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_ab1=itemView.findViewById(R.id.tv_tab1);
            tv_ab2=itemView.findViewById(R.id.tv_tab2);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_industry=itemView.findViewById(R.id.tv_industry);
            tv_othertitle=itemView.findViewById(R.id.tv_othertitle);
            tv_tab1 = itemView.findViewById(R.id.tv_tab1);
            tv_tab2 = itemView.findViewById(R.id.tv_tab2);
            tv_tab3 = itemView.findViewById(R.id.tv_tab3);
            ll_tag = itemView.findViewById(R.id.ll_tag);
        }
    }




}
