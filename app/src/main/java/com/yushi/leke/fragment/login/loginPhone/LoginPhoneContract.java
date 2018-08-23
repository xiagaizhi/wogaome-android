package com.yushi.leke.fragment.login.loginPhone;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface LoginPhoneContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {

        void onRegister();
        void onForgetPassword();
        void onClearPhone();
        void onClearPassword();
        void login(String phone,String password);

    }
}
