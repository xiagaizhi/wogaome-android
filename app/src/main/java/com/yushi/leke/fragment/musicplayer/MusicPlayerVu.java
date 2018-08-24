package com.yushi.leke.fragment.musicplayer;

import android.graphics.Bitmap;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.widget.ImageView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_musicplayer)
@Title("音乐播放器")
public class MusicPlayerVu extends BaseVu<MusicPlayerContract.Presenter> implements MusicPlayerContract.IView {
    @Override
    public void initView(View view) {

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return super.initTitle(appToolbar);
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }


    @Override
    public void setBackgroundImage(Bitmap bitmap) {

    }

    @Override
    public void onUpdateMediaDescription(MediaDescriptionCompat description) {

    }

    @Override
    public void onUpdateDuration(MediaMetadataCompat metadata) {

    }

    @Override
    public void updatePlaybackState(PlaybackStateCompat state) {

    }

    @Override
    public void updateProgress(int currentPosition) {

    }
}
