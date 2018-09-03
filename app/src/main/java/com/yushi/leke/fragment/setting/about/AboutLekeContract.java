package com.yushi.leke.fragment.setting.about;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface AboutLekeContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {

        void openPlayer();
    }
}
