package com.yushi.leke.fragment.checkPhone;

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
       void getVerifcationCode(String phoneNumber);
       void checkPhone(String phoneNumber,String code);
    }
}
