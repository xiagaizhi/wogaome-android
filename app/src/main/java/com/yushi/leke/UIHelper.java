package com.yushi.leke;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yufan.library.bean.FragmentInfo;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.activity.MainActivity;
import com.yushi.leke.activity.MusicPlayerActivity;

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

    public static ImageView getMusicView(Activity activity, AppToolbar toolbar) {
        ImageView musicView = null;
        if (activity instanceof MainActivity) {
            musicView = ((MainActivity) activity).getMusicView();
            if (musicView.getParent() != null) {
                ((ViewGroup) musicView.getParent()).removeView(musicView);
            }
            toolbar.getRightViewGroup().addView(musicView);
        }
        return musicView;
    }



}
