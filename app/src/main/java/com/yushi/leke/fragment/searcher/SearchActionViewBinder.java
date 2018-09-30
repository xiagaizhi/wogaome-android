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

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;
import com.yushi.leke.util.FormatImageUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 搜索主页 搜索活动
 */
public class SearchActionViewBinder extends ItemViewBinder<SearchActionInfo, SearchActionViewBinder.ViewHolder> {
    private ICallBack callBack;
    public SearchActionViewBinder(ICallBack callBack) {
        this.callBack=callBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_search_action, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SearchActionInfo category) {

        holder.sdv.setImageURI(FormatImageUtil.converImageUrl(category.horizontalIcon,256,144));
        holder.tv_title.setText(category.getTitle());
        holder.tv_organizer.setText(category.getOrganizer());
        holder.tv_time.setText(getFormatedDateTime("yy-MM-dd", Long.parseLong(category.getStartTime()))
                +" / "
                +getFormatedDateTime("MM-dd", Long.parseLong(category.getEndTime())));
        if (getAdapter().getItemCount()==getPosition(holder)-1) {
            holder.view_bottom_line.setVisibility(View.INVISIBLE);
        } else {
            holder.view_bottom_line.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBack!=null){
                    callBack.OnBackResult(category);
                }
            }
        });


    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdv;
        View view_bottom_line;
        TextView tv_title,tv_time,tv_organizer;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_organizer=itemView.findViewById(R.id.tv_organizer);
            view_bottom_line = itemView.findViewById(R.id.view_bottom_line);
        }
    }
    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime + 0));
    }


}
