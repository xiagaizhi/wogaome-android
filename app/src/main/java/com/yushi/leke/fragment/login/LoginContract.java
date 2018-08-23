package com.yushi.leke.fragment.login;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface LoginContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {
        void onRegisterClick();
        void onPhoneLoginClick();
        void onWeixinLoginClick();
        void onLogoClick();
        void onAgreementClick();

    }
}
