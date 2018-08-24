package com.yushi.leke.fragment.musicplayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface MusicPlayerContract {
    interface IView extends Vu {
        ImageView getBackgroundImage();
    }

    interface Presenter extends Pr {



    }
}
