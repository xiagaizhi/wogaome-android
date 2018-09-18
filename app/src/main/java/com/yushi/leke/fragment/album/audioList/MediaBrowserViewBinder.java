package com.yushi.leke.fragment.album.audioList;

import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yushi.leke.R;
import com.yushi.leke.fragment.test.CategoryItemViewBinder;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by mengfantao on 18/9/18.
 */

public class MediaBrowserViewBinder  extends ItemViewBinder<MediaBrowserCompat.MediaItem, MediaBrowserViewBinder.ViewHolder> {
    private OnItemClick clickListener;
    public MediaBrowserViewBinder(OnItemClick clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View view=layoutInflater.inflate(R.layout.item_media_detail,null);
        return new ViewHolder(view);
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, @NonNull final MediaBrowserCompat.MediaItem mediaItem) {
        viewHolder.tv_name.setText(mediaItem.getDescription().getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null){
                    clickListener.onClick(mediaItem);
                }
            }
        });
        viewHolder.sdv.setImageURI("http://icon.nipic.com/BannerPic/20180917/original/20180917090924_1.jpg");
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private SimpleDraweeView sdv;
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name= itemView.findViewById(R.id.tv_name);
            sdv=itemView.findViewById(R.id.sdv);
        }
    }

    public interface OnItemClick{
        void onClick(MediaBrowserCompat.MediaItem mediaItem);
    }
}
