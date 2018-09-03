package com.yushi.leke.fragment.setting.modifyLoginPwd;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface ModifyLoginPwdContract {
    interface IView extends Vu {
        void updataPhoneNumber(String phone);
    }

    interface Presenter extends Pr {
        boolean getVerifcationCode();

        void modifyLoginPwd(String code);
    }
}
