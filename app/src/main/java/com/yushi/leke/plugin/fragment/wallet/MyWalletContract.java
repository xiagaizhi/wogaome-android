package com.yushi.leke.plugin.fragment.wallet;

import com.yufan.library.inter.Pr;
import com.yufan.library.inter.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface MyWalletContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {

        void setRechargePwd();
    }
}
