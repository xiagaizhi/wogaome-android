package com.yushi.leke.fragment.resetPassword;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

import java.util.Map;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface ResetPasswordContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {
        void getVerifcationCode(String phone,String sessionID);
        void resetPassword(String phone,String vcode,String newPassword);

    }
}
