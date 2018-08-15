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

package com.yushi.leke.plugin.fragment.test;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yushi.leke.plugin.bean.Person;
import com.yushi.leke.plugin.R;

import me.drakeet.multitype.ItemViewBinder;


/**
 * @author drakeet
 */
public class CategoryItemViewBinder extends ItemViewBinder<Person, CategoryItemViewBinder.ViewHolder> {

  @Override
  protected @NonNull ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
   return null;
  }


  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Person category) {

    holder.tv_age.setText(category.getAge()+"");
  }


  static class ViewHolder extends RecyclerView.ViewHolder {



    private TextView tv_age;
    ViewHolder(@NonNull View itemView) {
      super(itemView);

    }
  }
}
