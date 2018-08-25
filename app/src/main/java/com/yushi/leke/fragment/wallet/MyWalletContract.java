package com.yushi.leke.fragment.wallet;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface MyWalletContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {

        void openPaySafety();
        void openPlayer();
        void openTreasureBox();
    }
}
