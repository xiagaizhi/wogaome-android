package com.yushi.leke.fragment.ucenter.personalInfo;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface PersonalInfoContract {
    interface IView extends Vu {
        void destoryBitmap();

    }

    interface Presenter extends Pr {

        void openPlayer();
    }
}
