package com.yushi.leke.plugin.fragment.resetpassword;

import com.yufan.library.base.Pr;
import com.yufan.library.inter.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface ResetPasswordContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {
        void getVerifcationCode();

    }
}
