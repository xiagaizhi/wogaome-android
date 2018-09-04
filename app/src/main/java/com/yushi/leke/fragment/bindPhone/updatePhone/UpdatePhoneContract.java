package com.yushi.leke.fragment.bindPhone.updatePhone;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface UpdatePhoneContract {
    interface IView extends Vu {

    }

    interface Presenter extends Pr {

        void getVerifcationCode(String phone);

        void updatePhone(String phone, String code);


    }
}
