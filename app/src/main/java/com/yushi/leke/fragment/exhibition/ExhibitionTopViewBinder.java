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

package com.yushi.leke.fragment.exhibition;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yushi.leke.R;
import com.yushi.leke.activity.MusicPlayerActivity;

import me.drakeet.multitype.ItemViewBinder;


/**
 * @author drakeet
 */
public class ExhibitionTopViewBinder extends ItemViewBinder<ExhibitionTopInfo, ExhibitionTopViewBinder.ViewHolder> {

    public ExhibitionTopViewBinder() {

    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_top_exhibition, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final ExhibitionTopInfo category) {
        holder.rightMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MusicPlayerActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        holder.rightMusic.setBackgroundResource(R.drawable.anim_music_player_icon);

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView rightMusic;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rightMusic=itemView.findViewById(R.id.iv_anim_icon);

        }
    }


}
