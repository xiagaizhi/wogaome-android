package com.yushi.leke.fragment.album;

import android.support.v4.view.ViewPager;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface AlbumDetailContract {
    interface IView extends Vu {
        void showtext(AlbumDetailinfo info);
        void showsubstate(int state);
        ViewPager getViewPager();
        SimpleDraweeView getDraweeView();
    }

    interface Presenter extends Pr {
    void onMusicMenuClick();
    void register();
    void unregister();
    void onShareclick();
    }
}
