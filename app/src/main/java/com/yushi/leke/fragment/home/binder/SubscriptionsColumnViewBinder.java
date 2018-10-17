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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;
import com.yushi.leke.fragment.home.bean.SubscriptionColumnInfo;

import me.drakeet.multitype.ItemViewBinder;


/**
 * 首页栏目标签
 */
public class SubscriptionsColumnViewBinder extends ItemViewBinder<SubscriptionColumnInfo, SubscriptionsColumnViewBinder.ViewHolder> {
    private ICallBack mCallBack;


    public SubscriptionsColumnViewBinder(ICallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_subscriptions_column, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SubscriptionColumnInfo subscriptionColumnInfo) {
        holder.tv_name.setText(subscriptionColumnInfo.getChannelName());
        if (subscriptionColumnInfo.getMore() == 1) {
            holder.tv_look_more.setVisibility(View.VISIBLE);
            holder.tv_look_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCallBack != null) {
                        mCallBack.OnBackResult(subscriptionColumnInfo);
                    }
                }
            });
        } else {
            holder.tv_look_more.setVisibility(View.GONE);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_look_more;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_look_more = itemView.findViewById(R.id.tv_look_more);
        }
    }


}
