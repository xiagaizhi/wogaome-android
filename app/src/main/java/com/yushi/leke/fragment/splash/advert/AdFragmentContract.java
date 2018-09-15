package com.yushi.leke.fragment.splash.advert;

import android.graphics.Bitmap;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface AdFragmentContract {
    interface IView extends Vu {

        void updateAd(AdInfo adInfo, Bitmap bitmap);
        void updateCountdown(int seconds);
    }

    interface Presenter extends Pr {
        void jumpToMain();
        void openAdDetail();
    }
}
