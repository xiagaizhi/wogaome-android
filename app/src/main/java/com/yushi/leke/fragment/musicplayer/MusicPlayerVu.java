package com.yushi.leke.fragment.musicplayer;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.UIHelper;
import com.yushi.leke.uamp.MusicService;
import com.yushi.leke.uamp.utils.LogHelper;
import com.yushi.leke.widget.AlbumViewPager;
import com.yushi.leke.widget.MyScroller;
import com.yushi.leke.widget.PlayerSeekBar;

import java.lang.reflect.Field;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_musicplayer)
@Title("音乐播放器")
public class MusicPlayerVu extends BaseVu<MusicPlayerContract.Presenter> implements MusicPlayerContract.IView {
    @FindView(R.id.view_pager)
    AlbumViewPager mViewPager;
    @FindView(R.id.iv_needle)
    ImageView mNeedle;
    @FindView(R.id.playing_playlist)
    ImageView playing_playlist;
    @FindView(R.id.play_seek)
    PlayerSeekBar mSeekbar;
    @FindView(R.id.music_duration)
    TextView mEnd;
    @FindView(R.id.playing_pre)
    ImageView mSkipPrev;
    @FindView(R.id.playing_play)
    ImageView mPlayPause;
    @FindView(R.id.playing_next)
    ImageView mSkipNext;
    @FindView(R.id.playing_fav)
    ImageView playing_fav;
    @FindView(R.id.tv_album_name)
    TextView tv_album_name;
    @FindView(R.id.music_duration_played)
    TextView mDuration;
    @FindView(R.id.albumArt)
    ImageView albumArt;

    private PlaybackStateCompat mLastPlaybackState;

    @Override
    public void initView(View view) {
        playing_playlist.setOnClickListener(this);
        mSkipPrev.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mSkipNext.setOnClickListener(this);
        playing_fav.setOnClickListener(this);
        mPersenter.onSeekBarChangeListener(mDuration, mSeekbar);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        //UIHelper.getMusicView(mPersenter.getActivity(),appToolbar,MusicPlayerFragment.class);
      TextView titltView=  appToolbar.creatCenterView(TextView.class);
        titltView.setTextColor(Color.WHITE);
        titltView.setText("音乐播放器");
        ImageView leftView = appToolbar.creatLeftView(ImageView.class);
        leftView.setImageResource(R.drawable.left_back_white_arrows);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.finish();
            }
        });
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
            case R.id.playing_pre:
                mPersenter.pre();
                break;
            case R.id.playing_play:
                mPersenter.play();
                break;
            case R.id.playing_next:

                mPersenter.next();
                break;
            case R.id.playing_fav:
                mPersenter.fav();
                break;
        }
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }


    @Override
    public void setBackgroundImage(Bitmap bitmap) {
        albumArt.setImageBitmap(bitmap);
    }

    @Override
    public void onUpdateMediaDescription(MediaDescriptionCompat description) {
        StringBuffer sb = new StringBuffer();
        sb.append(description.getTitle());
        sb.append("-");
        sb.append(description.getSubtitle());
        tv_album_name.setText(sb.toString());
    }

    @Override
    public void onUpdateDuration(MediaMetadataCompat metadata) {
        if (metadata == null) {
            return;
        }
        int duration = (int) metadata.getDescription().getExtras().getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        mSeekbar.setMax(duration);
        mEnd.setText(DateUtils.formatElapsedTime(duration / 1000));
    }

    @Override
    public void updatePlaybackState(PlaybackStateCompat state) {
        if (state == null) {
            return;
        }
        mLastPlaybackState = state;


        switch (state.getState()) {
            case PlaybackStateCompat.STATE_PLAYING:

                mPlayPause.setSelected(false);
                mPersenter.scheduleSeekbarUpdate();

                break;
            case PlaybackStateCompat.STATE_PAUSED:

                mPlayPause.setSelected(true);
                mPersenter.stopSeekbarUpdate();

                break;
            case PlaybackStateCompat.STATE_NONE:
            case PlaybackStateCompat.STATE_STOPPED:

                mPlayPause.setSelected(true);
                mPersenter.stopSeekbarUpdate();

                break;
            case PlaybackStateCompat.STATE_BUFFERING:
                mPlayPause.setSelected(true);
                mPersenter.stopSeekbarUpdate();
                break;
            default:

        }

        mSkipNext.setVisibility((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_NEXT) == 0
                ? INVISIBLE : VISIBLE);
        mSkipPrev.setVisibility((state.getActions() & PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS) == 0
                ? INVISIBLE : VISIBLE);
    }

    @Override
    public void updateProgress(int currentPosition) {

        mSeekbar.setProgress((int) currentPosition);
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
