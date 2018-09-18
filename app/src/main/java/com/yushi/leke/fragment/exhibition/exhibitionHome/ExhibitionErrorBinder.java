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

import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;

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
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ExhibitionErrorInfo category) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mICallBack.OnBackResult();
            }
        });
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
