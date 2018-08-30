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

package com.yushi.leke.fragment.home.exhibition;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;


/**
 * @author drakeet
 */
public class ExhibitionViewBinder extends ItemViewBinder<ExhibitionInfo, ExhibitionViewBinder.ViewHolder> {

    public ExhibitionViewBinder() {

    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_exhibition, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ExhibitionInfo category) {
        holder.sdv.setImageURI(Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535627957733&di=8b95349d33b93897f2ffed94029cc448&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F17%2F76%2F54%2F02e58PICQJd_1024.jpg"));

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView sdv;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

      sdv=  itemView.findViewById(R.id.sdv);
        }
    }


}
