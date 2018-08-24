package com.yushi.leke.fragment.login;

import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface LoginContract {
    interface IView extends Vu {
        void showServiceSelector(int index);
    }

    interface Presenter extends Pr {
        void onRegisterClick();
        void onPhoneLoginClick();
        void onWeixinLoginClick();
        void onServiceSelector(@NonNull MaterialDialog dialog, @NonNull DialogAction which);
        void onAgreementClick();
        void onLogoClick();

    }
}
