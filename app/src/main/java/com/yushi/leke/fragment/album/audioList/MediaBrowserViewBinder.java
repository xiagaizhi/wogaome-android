package com.yushi.leke.fragment.album.audioList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.Global;
import com.yufan.library.util.StringUtil;
import com.yushi.leke.R;
import com.yushi.leke.fragment.test.CategoryItemViewBinder;
import com.yushi.leke.uamp.model.MutableMediaMetadata;
import com.yushi.leke.uamp.utils.MediaIDHelper;

import me.drakeet.multitype.ItemViewBinder;

import static android.support.v4.media.session.PlaybackStateCompat.STATE_NONE;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PAUSED;
import static android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING;

/**
 * Created by mengfantao on 18/9/18.
 */

public class MediaBrowserViewBinder extends ItemViewBinder<MediaBrowserCompat.MediaItem, MediaBrowserViewBinder.ViewHolder> {
    private OnItemClick clickListener;
    private Activity activity;

    public MediaBrowserViewBinder(Activity activity, OnItemClick clickListener) {
        this.clickListener = clickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater layoutInflater, @NonNull ViewGroup viewGroup) {
        View view = layoutInflater.inflate(R.layout.item_media_detail, null);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, @NonNull final MediaBrowserCompat.MediaItem mediaItem) {
        viewHolder.tv_name.setText(mediaItem.getDescription().getTitle());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(mediaItem);

                }
            }
        });

        int state = getMediaItemState(activity, mediaItem);
        Drawable drawable = getDrawableByState(activity, state);
        viewHolder.iv_play_state.setImageDrawable(drawable);
        viewHolder.sdv.setImageURI(mediaItem.getDescription().getIconUri());
        viewHolder.pb_media.setMax(mediaItem.getDescription().getExtras().getInt(MediaMetadataCompat.METADATA_KEY_DURATION));
        viewHolder.tv_num.setText("未播放");
        viewHolder.tv_info.setText(StringUtil.stringForTime(mediaItem.getDescription().getExtras().getInt(MediaMetadataCompat.METADATA_KEY_DURATION)) + "/" + mediaItem.getDescription().getExtras().getInt(MutableMediaMetadata.viewPeople) + "人听过");

        int listenable = mediaItem.getDescription().getExtras().getInt(MutableMediaMetadata.listenable);
        int levelStatus = mediaItem.getDescription().getExtras().getInt(MutableMediaMetadata.levelStatus);
        Drawable drawableLeft;
        if (listenable == 1) {
            drawableLeft = activity.getResources().getDrawable(R.drawable.ic_play_headset_red);
            viewHolder.tv_action.setText("试听");
            viewHolder.tv_action.setTextColor(activity.getResources().getColor(R.color.alivc_red));
        } else {
            viewHolder.tv_action.setText("播放");
            if (levelStatus == 0) {//播放
                drawableLeft = activity.getResources().getDrawable(R.drawable.ic_play_unlock_blue);
                viewHolder.tv_action.setTextColor(activity.getResources().getColor(R.color.alivc_blue_levelf));
            } else {//不可播放
                drawableLeft = activity.getResources().getDrawable(R.drawable.ic_play_lock_gray);
                viewHolder.tv_action.setTextColor(activity.getResources().getColor(R.color.color_gray_level9));
            }
        }

        viewHolder.tv_action.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);


        if (levelStatus == 1) {
            viewHolder.tv_free.setText("青铜段位解锁");
        } else if (levelStatus == 2) {
            viewHolder.tv_free.setText("白银段位解锁");
        } else if (levelStatus == 3) {
            viewHolder.tv_free.setText("黄金段位解锁");
        } else if (levelStatus == 4) {
            viewHolder.tv_free.setText("铂金段位解锁");
        } else if (levelStatus == 5) {
            viewHolder.tv_free.setText("钻石段位解锁");
        } else if (levelStatus == 6) {
            viewHolder.tv_free.setText("至尊段位解锁");
        } else {
            viewHolder.tv_free.setText("免费");
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_free;
        private TextView tv_action;
        private TextView tv_info;
        private TextView tv_name;
        private ImageView iv_play_state;
        private SimpleDraweeView sdv;
        private TextView tv_num;
        private ProgressBar pb_media;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            sdv = itemView.findViewById(R.id.sdv);
            iv_play_state = itemView.findViewById(R.id.iv_play_state);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_info = itemView.findViewById(R.id.tv_info);
            tv_action = itemView.findViewById(R.id.tv_action);
            tv_free = itemView.findViewById(R.id.tv_free);
            pb_media = itemView.findViewById(R.id.pb_media);
        }
    }

    public interface OnItemClick {
        void onClick(MediaBrowserCompat.MediaItem mediaItem);
    }

    private Drawable getDrawableByState(Context context, int state) {

        switch (state) {
            case STATE_PLAYING:
                Drawable playingDrawable =
                        ContextCompat.getDrawable(context, R.drawable.ic_player_paused_detail);
                return playingDrawable;
            case STATE_PAUSED:
                Drawable playDrawable = ContextCompat.getDrawable(context,
                        R.drawable.ic_player_play_detail);

                return playDrawable;
            default:
                Drawable playDrawabled = ContextCompat.getDrawable(context,
                        R.drawable.ic_player_play_detail);

                return playDrawabled;
        }
    }

    private int getMediaItemState(Activity context, MediaBrowserCompat.MediaItem mediaItem) {
        int state = STATE_NONE;
        // Set state to playable first, then override to playing or paused state if needed
        if (mediaItem.isPlayable()) {

            if (MediaIDHelper.isMediaItemPlaying(context, mediaItem)) {
                state = getStateFromController(context);
            }
        }

        return state;
    }



    private int getStateFromController(Activity context) {
        MediaControllerCompat controller = MediaControllerCompat.getMediaController(context);
        PlaybackStateCompat pbState = controller.getPlaybackState();
        if (pbState == null ||
                pbState.getState() == PlaybackStateCompat.STATE_ERROR) {
            return STATE_NONE;
        } else if (pbState.getState() == STATE_PLAYING) {
            return STATE_PLAYING;
        } else {
            return STATE_PAUSED;
        }
    }
}
