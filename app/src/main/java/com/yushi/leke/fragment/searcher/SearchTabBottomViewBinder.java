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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

/**
 * 搜索主页 底部进入条目
 */
public class SearchTabBottomViewBinder extends ItemViewBinder<SearchBottomInfo, SearchTabBottomViewBinder.ViewHolder> {

    private ICallBack callBack;
    public static final int SEARCH_MORE_AUDIO=0;
    public static final int SEARCH_MORE_ACTIVITY=1;
    public SearchTabBottomViewBinder(ICallBack callBack) {
        this.callBack=callBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_search_tab_bottom, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SearchBottomInfo category) {
            if(category.isSubscriptions){
                holder.view_line.setVisibility(View.VISIBLE);
                holder.tv_title.setText("查看更多项目");
                holder.rl_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    if(callBack!=null){
                        callBack.OnBackResult(SEARCH_MORE_AUDIO);
                    }
                    }
                });
            }else {
                holder.view_line.setVisibility(View.INVISIBLE);
                holder.tv_title.setText("查看更多活动");
                holder.rl_more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(callBack!=null){
                            callBack.OnBackResult(SEARCH_MORE_ACTIVITY);
                        }
                    }
                });
            }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {
       View view_line;
        RelativeLayout rl_more;
        TextView tv_title;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_more=itemView.findViewById(R.id.rl_more);
            view_line=itemView.findViewById(R.id.view_line);
            tv_title=itemView.findViewById(R.id.tv_title);
        }
    }




}
