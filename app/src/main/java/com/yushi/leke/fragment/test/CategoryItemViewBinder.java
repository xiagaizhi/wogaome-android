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

package com.yushi.leke.fragment.test;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yushi.leke.R;

import me.drakeet.multitype.ItemViewBinder;


/**
 * @author drakeet
 */
public class CategoryItemViewBinder extends ItemViewBinder<MediaBrowserCompat.MediaItem, CategoryItemViewBinder.ViewHolder> {

  private OnItemClick clickListener;

  public CategoryItemViewBinder(OnItemClick clickListener) {
    this.clickListener = clickListener;
  }

  @Override
  protected @NonNull ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View view=inflater.inflate(R.layout.media_list_item,null);

   return new ViewHolder(view);
  }

  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final MediaBrowserCompat.MediaItem category) {

    holder.title.setText(category.getMediaId()+"");
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        clickListener.onClick(category);
      }
    });
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    ViewHolder(@NonNull View itemView) {
      super(itemView);

      title= itemView.findViewById(R.id.title);
    }
  }
  public interface OnItemClick{
   void onClick(MediaBrowserCompat.MediaItem mediaItem);
  }
}
