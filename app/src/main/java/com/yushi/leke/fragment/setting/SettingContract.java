package com.yushi.leke.fragment.setting;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;
import com.yushi.leke.dialog.update.UpdateInfo;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface SettingContract {
    interface IView extends Vu {
        void updataCacheSize(String cacheSize);
        void upDateVersion(UpdateInfo updateInfo);

    }

    interface Presenter extends Pr {
        void cleanMemoryCache();
        void logout();
        void upgrade();
        void lekeAbout();
        void accountAndSafety();
    }
}
