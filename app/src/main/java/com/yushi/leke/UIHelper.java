package com.yushi.leke;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yufan.library.bean.FragmentInfo;
import com.yufan.library.util.PxUtil;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.activity.MainActivity;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.uamp.playback.PlaybackManager;

import java.util.HashMap;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by mengfantao on 18/7/27.
 */

public class UIHelper {

    public static FragmentInfo creat(Class<? extends SupportFragment> clz) {
        FragmentInfo fragment = new FragmentInfo(clz);
        return fragment;
    }

    /**
     * 打开二级页面
     *
     * @param activity
     * @param fragment
     */
    public static void openFragment(FragmentActivity activity, Fragment fragment, boolean backStack) {
        openFragment(activity, fragment, R.id.activity_content_level1, backStack);
    }

    /**
     * 打开二级页面
     *
     * @param activity
     * @param fragment
     */
    public static void openFragment(FragmentActivity activity, Fragment fragment, int id, boolean backStack) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        //  beginTransaction.setCustomAnimations(R.anim.fragment_in,0,0,R.anim.fragment_out);
        beginTransaction.add(id, fragment, fragment.getClass().getName());
        if (backStack) {
            beginTransaction.addToBackStack(fragment.getTag());
        }
        beginTransaction.commitAllowingStateLoss();
    }

    public static ImageView getWhiteMusicView(Activity activity, AppToolbar toolbar,Class clz) {
        ImageView  musicView=null;
        if(activity instanceof MainActivity){
            musicView = ((MainActivity)activity).getMusicView(activity,clz);
        }
        toolbar.getRightViewGroup().addView(musicView);
        musicView.setImageResource(R.drawable.anim_player_white);
        if(PlaybackManager.getManager().isPlaying()){
            ((AnimationDrawable) musicView.getDrawable()).start();
        }
        return musicView;
    }

    public static ImageView getMusicView(Activity activity, AppToolbar toolbar,Class clz) {
        ImageView  musicView=null;
        if(activity instanceof MainActivity){
              musicView = ((MainActivity)activity).getMusicView(activity,clz);
        }
        toolbar.getRightViewGroup().addView(musicView);
        musicView.setImageResource(R.drawable.anim_player_blue);
        MediaControllerCompat mediaControllerCompat=  MediaControllerCompat.getMediaController(activity);
        if(mediaControllerCompat!=null&&mediaControllerCompat.getPlaybackState().getState()== PlaybackStateCompat.STATE_PLAYING){
            ((AnimationDrawable) musicView.getDrawable()).start();
        }

        return musicView;
    }

    public static void putImageView(Activity activity,ImageView imageView, Class clz){
        if(activity instanceof MainActivity){
            ((MainActivity)activity).putImageView(imageView,clz);
            MediaControllerCompat mediaControllerCompat=  MediaControllerCompat.getMediaController(activity);
            if(mediaControllerCompat!=null&&mediaControllerCompat.getPlaybackState().getState()== PlaybackStateCompat.STATE_PLAYING){
                ((AnimationDrawable) imageView.getDrawable()).start();
            }
        }
    }



}
