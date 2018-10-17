package com.yushi.leke.fragment.bindPhone.checkPhone;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface CheckPhoneContract {
    interface IView extends Vu {
        void returnPhoneNumber(String phoneNumber);

    }

    interface Presenter extends Pr {
       boolean getVerifcationCode(String sessionId);
       void checkPhone(String code);
       String getPhone();
    }
}
