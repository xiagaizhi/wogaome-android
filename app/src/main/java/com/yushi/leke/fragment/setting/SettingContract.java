package com.yushi.leke.fragment.setting;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface SettingContract {
    interface IView extends Vu {
        void updataCacheSize(String cacheSize);

    }

    interface Presenter extends Pr {
        void cleanMemoryCache();
    }
}
