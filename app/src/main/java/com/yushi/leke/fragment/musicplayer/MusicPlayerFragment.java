package com.yushi.leke.fragment.musicplayer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.uamp.AlbumArtCache;
import com.yushi.leke.uamp.MusicService;
import com.yushi.leke.uamp.model.MusicProvider;
import com.yushi.leke.uamp.utils.LogHelper;
import com.yushi.leke.widget.MyScroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(MusicPlayerVu.class)
public class MusicPlayerFragment extends BaseFragment<MusicPlayerContract.IView> implements MusicPlayerContract.Presenter {
    private final Handler mHandler = new Handler();
    private static final long PROGRESS_UPDATE_INTERNAL = 1000;
    private static final long PROGRESS_UPDATE_INITIAL_INTERVAL = 100;
    public static final String EXTRA_CURRENT_MEDIA_DESCRIPTION="EXTRA_CURRENT_MEDIA_DESCRIPTION";
    private MediaBrowserCompat mMediaBrowser;
    private String mCurrentArtUrl;
    private static final String TAG = LogHelper.makeLogTag(MusicPlayerFragment.class);
    private final Runnable mUpdateProgressTask = new Runnable() {
        @Override
        public void run() {
            updateProgress();
        }
    };
    private MusicFragmentAdapter mAdapter;
    private final ScheduledExecutorService mExecutorService =
            Executors.newSingleThreadScheduledExecutor();
    private     PlayQueueFragment   playQueueFragment;
    private ScheduledFuture<?> mScheduleFuture;
    private PlaybackStateCompat mLastPlaybackState;

    private final MediaControllerCompat.Callback mCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(@NonNull PlaybackStateCompat state) {
            LogHelper.d(TAG, "onPlaybackstate changed", state);
            updatePlaybackState(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            if (metadata != null) {
                updateMediaDescription(metadata.getDescription());
                updateDuration(metadata);
            }
        }
    };

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallback =
            new MediaBrowserCompat.ConnectionCallback() {
                @Override
                public void onConnected() {
                    LogHelper.d(TAG, "onConnected");
                    try {
                        connectToSession(mMediaBrowser.getSessionToken());
                    } catch (RemoteException e) {
                        LogHelper.e(TAG, e, "could not connect media controller");
                    }
                }
            };




    @Override
    public void onStart() {
        super.onStart();
        if (mMediaBrowser != null) {
            mMediaBrowser.connect();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMediaBrowser != null) {
            mMediaBrowser.disconnect();
        }
        MediaControllerCompat controllerCompat = MediaControllerCompat.getMediaController(getActivity());
        if (controllerCompat != null) {
            controllerCompat.unregisterCallback(mCallback);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSeekbarUpdate();
        mExecutorService.shutdown();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Only update from the intent if we are not recreating from a config change:
        if (savedInstanceState == null) {
            updateFromParams(getArguments());
        }

        mMediaBrowser = new MediaBrowserCompat(getContext(),
                new ComponentName(getContext(), MusicService.class), mConnectionCallback, null);
        getVu().getViewPager().setOffscreenPageLimit(2);
        mAdapter = new MusicPlayerFragment.MusicFragmentAdapter(getChildFragmentManager());
        getVu().getViewPager().setAdapter(mAdapter);
        PlaybarPagerTransformer transformer = new PlaybarPagerTransformer();
        getVu().getViewPager().setPageTransformer(true, transformer);
        // 改变viewpager动画时间
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            MyScroller mScroller = new MyScroller(getContext(), new LinearInterpolator());
            mField.set(getVu().getViewPager(), mScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        getVu().getViewPager().addOnPageChangeListener(onPageChangeListener);

    }
    ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {

        private int preState=-1;
        private int pPosition;
        @Override
        public void onPageSelected(final int pPosition) {
            this.pPosition=pPosition;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int pState) {
            if(pState==ViewPager.SCROLL_STATE_DRAGGING&&preState==ViewPager.SCROLL_STATE_IDLE){
                preState=ViewPager.SCROLL_STATE_DRAGGING;
                animScrollDragging();
            }else if(pState==ViewPager.SCROLL_STATE_IDLE){
                preState=ViewPager.SCROLL_STATE_IDLE;
                animScrollIdle();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MediaControllerCompat.TransportControls controls = MediaControllerCompat.getMediaController(getActivity()).getTransportControls();
                        controls.skipToQueueItem(pPosition);
                    }
                },300);
            }
        }
    };
    private void animScrollDragging(){
        Fragment fragment = (RoundFragment) getVu().getViewPager().getAdapter().instantiateItem(getVu().getViewPager(), getVu().getViewPager().getCurrentItem());
        ObjectAnimator   mRotateAnim = (ObjectAnimator) fragment.getView().getTag(R.id.tag_animator);
        if (mRotateAnim != null) {
            mRotateAnim.cancel();
            float valueAvatar = (float) mRotateAnim.getAnimatedValue();
            mRotateAnim.setFloatValues(valueAvatar, 360f + valueAvatar);
            Log.d("valueAvatar","valueAvatar"+valueAvatar);
        }
        ObjectAnimator animator=   ObjectAnimator.ofFloat(getVu().getNeedleImageView(), "rotation", 0,-25);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void animScrollIdle(){
        Fragment fragment = (RoundFragment) getVu().getViewPager().getAdapter().instantiateItem(getVu().getViewPager(), getVu().getViewPager().getCurrentItem());
        ObjectAnimator   mRotateAnim = (ObjectAnimator) fragment.getView().getTag(R.id.tag_animator);
        ObjectAnimator animator=    ObjectAnimator.ofFloat(getVu().getNeedleImageView(), "rotation", -25, 0);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        if (mRotateAnim != null) {
            AnimatorSet  mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(mRotateAnim,animator);
            mAnimatorSet.start();
        }
    }
    private void connectToSession(MediaSessionCompat.Token token) throws RemoteException {
        MediaControllerCompat mediaController = new MediaControllerCompat(
                getContext(), token);
        if (mediaController.getMetadata() == null) {
           onBackPressed();
            return;
        }
        MediaControllerCompat.setMediaController(getActivity(), mediaController);
        mediaController.registerCallback(mCallback);

        PlaybackStateCompat state = mediaController.getPlaybackState();
        playQueueFragment = new PlayQueueFragment();
        playQueueFragment.setQueue(mediaController.getQueue());
        updatePlaybackState(state);
        if(state.getState()==PlaybackStateCompat.STATE_PLAYING){
            animScrollIdle();
        }
        MediaMetadataCompat metadata = mediaController.getMetadata();
        if (metadata != null) {
            updateMediaDescription(metadata.getDescription());
            updateDuration(metadata);
        }

        updateProgress();
        if (state != null && (state.getState() == PlaybackStateCompat.STATE_PLAYING ||
                state.getState() == PlaybackStateCompat.STATE_BUFFERING)) {
            scheduleSeekbarUpdate();
        }
    }

    private void updateFromParams(Bundle intent) {
        if (intent != null) {
            MediaDescriptionCompat description = intent.getParcelable(
                    EXTRA_CURRENT_MEDIA_DESCRIPTION);
            if (description != null) {
                updateMediaDescription(description);
            }
        }
    }

    private void scheduleSeekbarUpdate() {
        stopSeekbarUpdate();
        if (!mExecutorService.isShutdown()) {
            mScheduleFuture = mExecutorService.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            mHandler.post(mUpdateProgressTask);
                        }
                    }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS);
        }
    }

    private void stopSeekbarUpdate() {
        if (mScheduleFuture != null) {
            mScheduleFuture.cancel(false);
        }
    }

    private void fetchImageAsync(@NonNull MediaDescriptionCompat description) {
        if (description.getIconUri() == null) {
            return;
        }
        String artUrl = description.getIconUri().toString();
        mCurrentArtUrl = artUrl;
        AlbumArtCache cache = AlbumArtCache.getInstance();
        Bitmap art = cache.getBigImage(artUrl);
        if (art == null) {
            art = description.getIconBitmap();
        }
        if (art != null) {
            // if we have the art cached or from the MediaDescription, use it:
            getVu().setBackgroundImage(art);

        } else {
            // otherwise, fetch a high res version and update:
            cache.fetch(artUrl, new AlbumArtCache.FetchListener() {
                @Override
                public void onFetched(String artUrl, Bitmap bitmap, Bitmap icon) {
                    // sanity check, in case a new fetch request has been done while
                    // the previous hasn't yet returned:
                    if (artUrl.equals(mCurrentArtUrl)) {
                        getVu().setBackgroundImage(bitmap);
                    }
                }
            });
        }
    }

    private void updateMediaDescription(MediaDescriptionCompat description) {
        if (description == null) {
            return;
        }
        LogHelper.d(TAG, "updateMediaDescription called ");
        getVu().onUpdateMediaDescription(description);
        fetchImageAsync(description);

    }

    private void updateDuration(MediaMetadataCompat metadata) {
        if (metadata == null) {
            return;
        }
        getVu().onUpdateDuration(metadata);
        LogHelper.d(TAG, "updateDuration called ");
        int duration = (int) metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
    }

    private void updatePlaybackState(PlaybackStateCompat state) {
        if (state == null) {
            return;
        }

        mLastPlaybackState = state;
      getVu().updatePlaybackState(state);
      if(playQueueFragment!=null){
          playQueueFragment.setCurrentQueue(state.getActiveQueueItemId());
      }

    }

    private void updateProgress() {
        if (mLastPlaybackState == null) {
            return;
        }
        long currentPosition = mLastPlaybackState.getPosition();
        if (mLastPlaybackState.getState() == PlaybackStateCompat.STATE_PLAYING) {
            // Calculate the elapsed time between the last position update and now and unless
            // paused, we can assume (delta * speed) + current position is approximately the
            // latest position. This ensure that we do not repeatedly call the getPlaybackState()
            // on MediaControllerCompat.
            long timeDelta = SystemClock.elapsedRealtime() -
                    mLastPlaybackState.getLastPositionUpdateTime();
            currentPosition += (int) timeDelta * mLastPlaybackState.getPlaybackSpeed();
        }
        getVu().updateProgress((int)currentPosition);
    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void showMusicListDialog() {

        playQueueFragment.show(getChildFragmentManager(), "PlayQueueFragment");
    }

    @Override
    public void play() {
        PlaybackStateCompat state = MediaControllerCompat.getMediaController(getActivity()).getPlaybackState();
        if (state != null) {
            MediaControllerCompat.TransportControls controls = MediaControllerCompat.getMediaController(getActivity()).getTransportControls();
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING: // fall through
                case PlaybackStateCompat.STATE_BUFFERING:
                    controls.pause();
                    stopSeekbarUpdate();

                    break;
                case PlaybackStateCompat.STATE_PAUSED:
                case PlaybackStateCompat.STATE_STOPPED:
                    controls.play();
                    scheduleSeekbarUpdate();
                    break;
                default:
                    LogHelper.d(TAG, "onClick with state ", state.getState());
            }
        }

    }

    @Override
    public void next() {
//        MediaControllerCompat.TransportControls controls = MediaControllerCompat.getMediaController(getActivity()).getTransportControls();
//        controls.skipToNext();
        animScrollDragging();
        getVu().getViewPager().setCurrentItem(getVu().getViewPager().getCurrentItem()+1,true);
    }

    @Override
    public void pre() {
        animScrollDragging();
        getVu().getViewPager().setCurrentItem(getVu().getViewPager().getCurrentItem()-1,true);
//        MediaControllerCompat.TransportControls controls = MediaControllerCompat.getMediaController(getActivity()).getTransportControls();
//        controls.skipToPrevious();
    }

    @Override
    public void onSeekBarChangeListener(final TextView mStart, SeekBar mSeekbar) {
        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mStart.setText(DateUtils.formatElapsedTime(progress / 1000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopSeekbarUpdate();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MediaControllerCompat.getMediaController(getActivity()).getTransportControls().seekTo(seekBar.getProgress());
                scheduleSeekbarUpdate();
            }
        });
    }

    @Override
    public void fav() {

    }

    @Override
    public void finish() {
        if(getActivity()!=null){
            getActivity().finish();
        }
    }

    public class MusicFragmentAdapter extends FragmentPagerAdapter {
        public MusicFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
          //  queue.get(position).getDescription();
            Fragment fragment=new RoundFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return 100;
        }
    }



    public class PlaybarPagerTransformer implements ViewPager.PageTransformer {


        @Override
        public void transformPage(View view, float position) {

        }

    }
}
