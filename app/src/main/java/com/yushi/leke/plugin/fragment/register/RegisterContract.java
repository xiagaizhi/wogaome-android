package com.yushi.leke.plugin.fragment.register;

import com.yufan.library.base.Pr;
import com.yufan.library.inter.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface RegisterContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {
        void getVerifcationCode();

    }
}
