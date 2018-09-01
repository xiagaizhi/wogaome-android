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

package com.yushi.leke.fragment.ucenter.personalInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.yufan.library.inter.ICallBack;
import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;

public class PersonalInfoViewBinder extends ItemViewBinder<PersonalItem, PersonalInfoViewBinder.ViewHolder> {
    private ICallBack callBack;
    private Context mContext;

    public PersonalInfoViewBinder(ICallBack callBack, Context context) {
        this.callBack = callBack;
        this.mContext = context;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_personal_info, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final PersonalItem category) {
        holder.tv_name.setText(category.tabName);
        holder.et_value.setText(category.tabValue);
        holder.et_value.setHint(category.tabEditHint);
        holder.tv_value.setText(category.tabValue);

        if (category.inputTab) {
            holder.tv_value.setVisibility(View.GONE);
            holder.et_value.setVisibility(View.VISIBLE);
            holder.et_value.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        callBack.OnBackResult(category, holder.et_value);
                    }
                }
            });
        } else {
            holder.tv_value.setVisibility(View.VISIBLE);
            holder.et_value.setVisibility(View.GONE);
        }
        if (category.tabExt) {
            holder.tv_ext.setVisibility(View.VISIBLE);
        } else {
            holder.tv_ext.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals("用户ID:", category.tabName) ||
                        TextUtils.equals("性别:", category.tabName) ||
                        TextUtils.equals("城市:", category.tabName)) {
                    callBack.OnBackResult(category,holder.et_value);
                }
            }
        });

        holder.tv_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals("用户ID:", category.tabName)) {
                    callBack.OnBackResult(category,holder.et_value);
                }
            }
        });
        holder.tv_ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals("用户ID:", category.tabName)) {
                    callBack.OnBackResult(category,holder.et_value);
                }
            }
        });

        if (TextUtils.equals("城市:", category.tabName)) {
            if (TextUtils.equals("请选择城市", category.tabValue)) {
                holder.tv_value.setTextColor(mContext.getResources().getColor(R.color.color_gray_level9));
            } else {
                holder.tv_value.setTextColor(mContext.getResources().getColor(R.color.color_gray_level3));
            }
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        TextView tv_value;
        TextView tv_ext;
        EditText et_value;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_value = itemView.findViewById(R.id.tv_value);
            tv_ext = itemView.findViewById(R.id.tv_ext);
            et_value = itemView.findViewById(R.id.et_value);
        }
    }


}
