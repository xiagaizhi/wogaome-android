package com.yushi.leke.fragment.resetPassword;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface ResetPasswordContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {
        void getVerifcationCode();
        void resetPassword(String phone,String vcode,String newPassword);

    }
}
