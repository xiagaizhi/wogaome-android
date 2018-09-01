package com.yushi.leke.fragment.ucenter.personalInfo;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;
import com.yufan.library.base.VuList;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface PersonalInfoContract {
    interface IView extends VuList {

    }

    interface Presenter extends Pr {

        void openPlayer();
        void resetTab();
        void upTab(PersonalItem personalItem);
        void resetVu();
    }
}
