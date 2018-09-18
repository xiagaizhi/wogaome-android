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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;


/**
 * 路演大厅 没有活动的空页面展示
 */
public class ExhibitionEmptyBinder extends ItemViewBinder<ExhibitionEmptyInfo, ExhibitionEmptyBinder.ViewHolder> {


    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_exhibition_empty, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ExhibitionEmptyInfo category) {
        holder.sdv.setImageURI("res://com.yushi.leke/"+R.drawable.empty_activity_prepare);
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
        }
    }


}
