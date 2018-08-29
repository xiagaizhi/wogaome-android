package com.yushi.leke.fragment.bindPhone;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by zhanyangyang on 18/8/25.
 */

public interface BindPhoneContract {
    interface IView extends Vu {
        void connectData(boolean isSafetyCheck,boolean isNeedReturnState, String phoneNumber);
    }

    interface Presenter extends Pr {
        void getVerifcationCode(String phone);

        void bindPhone(String phone, String code);
    }
}
