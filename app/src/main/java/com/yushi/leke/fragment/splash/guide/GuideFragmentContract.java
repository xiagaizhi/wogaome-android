package com.yushi.leke.fragment.splash.guide;

import android.view.View;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface GuideFragmentContract {
    interface IView extends Vu {
    }

    interface Presenter extends Pr {
        void jumpToMain();
    }
}
