package com.yushi.leke.fragment.musicplayer;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;
import com.yushi.leke.widget.AlbumViewPager;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface MusicPlayerContract {
    interface IView extends Vu {
        void setBackgroundImage(Bitmap bitmap);

        void onUpdateMediaDescription(MediaDescriptionCompat description);

        void onUpdateDuration(MediaMetadataCompat metadata);

        void updatePlaybackState(PlaybackStateCompat state);

        void updateProgress(int currentPosition);

        AlbumViewPager getViewPager();

        ImageView getNeedleImageView();
        void setAlbumName(String albumName);
        void setSubState(String state);
        void setCanOperation(boolean isCanOperation);
    }

    interface Presenter extends Pr {
        void showMusicListDialog();
        void play();
        void next();
        void pre();
        void onSeekBarChangeListener(TextView mStart, SeekBar mSeekbar);
        void fav();

        void finish();
        void scheduleSeekbarUpdate();
        void stopSeekbarUpdate();
    }
}
