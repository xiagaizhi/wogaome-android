package com.yushi.leke.fragment.musicplayer;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.Global;
import com.yufan.library.manager.SPManager;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.widget.AlbumViewPager;
import com.yushi.leke.widget.PlayerSeekBar;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_musicplayer)
@Title("音乐播放器")
public class MusicPlayerVu extends BaseVu<MusicPlayerContract.Presenter> implements MusicPlayerContract.IView {
    @FindView(R.id.img_music_guide)
    ImageView img_music_guide;
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
    TextView titltView;
    private String albumName;
    private boolean isCanOperation;

    @Override
    public void initView(View view) {
        playing_playlist.setOnClickListener(this);
        mSkipPrev.setOnClickListener(this);
        mPlayPause.setOnClickListener(this);
        mPlayPause.setSelected(true);
        mSkipNext.setOnClickListener(this);
        playing_fav.setOnClickListener(this);
        mPersenter.onSeekBarChangeListener(mDuration, mSeekbar);
        if (!SPManager.getInstance().getBoolean(Global.SP_KEY_MUSIC_PLAYER_GUIDE,false)){
            img_music_guide.setVisibility(VISIBLE);
            SPManager.getInstance().saveValue(Global.SP_KEY_MUSIC_PLAYER_GUIDE,true);
        }else {
            img_music_guide.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        //UIHelper.getMusicView(mPersenter.getActivity(),appToolbar,MusicPlayerFragment.class);
        titltView = appToolbar.creatCenterView(TextView.class);
        titltView.setTextColor(Color.WHITE);
        if (TextUtils.isEmpty(albumName)) {
            titltView.setText("音乐播放器");
        } else {
            titltView.setText(albumName);
        }

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
        if (isCanOperation) {
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
        tv_album_name.setText(description.getTitle());
    }

    @Override
    public void onUpdateDuration(MediaMetadataCompat metadata) {
        if (metadata == null) {
            return;
        }
        int duration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        mSeekbar.setMax(duration);
        mEnd.setText(DateUtils.formatElapsedTime(duration / 1000));
    }

    @Override
    public void updatePlaybackState(PlaybackStateCompat state) {
        if (state == null) {
            return;
        }

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

        mSeekbar.setProgress(currentPosition);
    }

    @Override
    public AlbumViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public ImageView getNeedleImageView() {
        return mNeedle;
    }

    @Override
    public void setAlbumName(String name) {
        albumName = name;
        if (titltView != null) {
            titltView.setText(name);
        }
    }

    @Override
    public void setSubState(String state) {
        if (TextUtils.equals(state, "0")) {//未订阅
            playing_fav.setSelected(false);
            playing_fav.setVisibility(VISIBLE);
        } else if (TextUtils.equals(state, "1")) {//已经订阅
            playing_fav.setSelected(true);
            playing_fav.setVisibility(VISIBLE);
        }
    }

    @Override
    public void setCanOperation(boolean isCanOperation) {
        this.isCanOperation = isCanOperation;
    }

    @Override
    public void hideGuide() {
        img_music_guide.setVisibility(View.GONE);
    }
}
