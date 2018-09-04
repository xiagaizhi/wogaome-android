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

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushi.leke.R;
import com.yushi.leke.fragment.home.SubscriptionInfo;

import me.drakeet.multitype.ItemViewBinder;

public class SearchActionViewBinder extends ItemViewBinder<SearchActionInfo, SearchActionViewBinder.ViewHolder> {

    public SearchActionViewBinder() {

    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_search_action, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SearchActionInfo category) {

        holder.sdv.setImageURI(Uri.parse("http://pic177.nipic.com/file/20180819/21016066_152012729087_2.jpg"));
        if (category.isLast) {
            holder.view_bottom_line.setVisibility(View.INVISIBLE);
        } else {
            holder.view_bottom_line.setVisibility(View.VISIBLE);
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdv;
        View view_bottom_line;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
            view_bottom_line = itemView.findViewById(R.id.view_bottom_line);
        }
    }


}
