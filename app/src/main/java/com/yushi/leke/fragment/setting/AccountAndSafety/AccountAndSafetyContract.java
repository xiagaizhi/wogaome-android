package com.yushi.leke.fragment.setting.AccountAndSafety;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface AccountAndSafetyContract {
    interface IView extends Vu {
        void updatePage(String phone);
    }

    interface Presenter extends Pr {

        void openBindPhone();

        void modifyPwd();
    }
}
