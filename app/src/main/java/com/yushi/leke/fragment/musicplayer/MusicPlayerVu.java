package com.yushi.leke.fragment.musicplayer;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.widget.AlbumViewPager;
import com.yushi.leke.widget.MyScroller;

import java.lang.reflect.Field;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_musicplayer)
@Title("音乐播放器")
public class MusicPlayerVu extends BaseVu<MusicPlayerContract.Presenter> implements MusicPlayerContract.IView {
    @FindView(R.id.view_pager)
    AlbumViewPager mViewPager;
    @FindView(R.id.needle)
    ImageView mNeedle;
    @FindView(R.id.playing_playlist)
    ImageView playing_playlist;

    @Override
    public void initView(View view) {
        playing_playlist.setOnClickListener(this);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        super.initTitle(appToolbar);
        appToolbar.build(false);
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.playing_playlist:
                mPersenter.showMusicListDialog();

                break;
        }
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

    @Override
    public AlbumViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public ImageView getNeedleImageView() {
        return mNeedle;
    }


}
