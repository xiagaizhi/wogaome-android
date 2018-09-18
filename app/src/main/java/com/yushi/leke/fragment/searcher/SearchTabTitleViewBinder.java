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

package com.yushi.leke.fragment.searcher;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 搜索主页 头部标题
 */
public class SearchTabTitleViewBinder extends ItemViewBinder<String, SearchTabTitleViewBinder.ViewHolder> {

    public SearchTabTitleViewBinder() {

    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_search_tab_title, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final String category) {
        holder.tv_tab_title.setText(category);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tab_title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tab_title=itemView.findViewById(R.id.tv_tab_title);
        }
    }




}
