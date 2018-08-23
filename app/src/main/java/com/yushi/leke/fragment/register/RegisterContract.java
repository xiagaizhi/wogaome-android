package com.yushi.leke.fragment.register;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface RegisterContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {
        void getVerifcationCode();
        void verify();
        void register(String phone,String password,String verifcationCode);

    }
}
