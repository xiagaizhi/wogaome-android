package com.yushi.leke.fragment.setting.about;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;
import com.yushi.leke.dialog.update.UpdateInfo;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface AboutLekeContract {
    interface IView extends Vu {
        void returnUpdateInfo(UpdateInfo updateInfo);

    }

    interface Presenter extends Pr {

        void openPlayer();
        void checkUpdate();
        void toUpgrade();
    }
}
