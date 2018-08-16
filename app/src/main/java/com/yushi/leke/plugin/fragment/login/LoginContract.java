package com.yushi.leke.plugin.fragment.login;

import com.yufan.library.inter.Pr;
import com.yufan.library.inter.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface LoginContract {
    interface View extends Vu {

    }

    interface Presenter extends Pr {
        void onKeyboardShow(int keyHeight);
        void onKeyboardHide();
        void onKeyboardShowOver();
        void onRegister();
        void onForgetPassword();
        void onLogin(String phone ,String password);

    }
}
