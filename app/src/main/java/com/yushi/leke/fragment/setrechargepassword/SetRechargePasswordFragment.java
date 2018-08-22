package com.yushi.leke.fragment.setRechargePassword;


import com.yufan.library.base.BaseFragment;
import android.view.WindowManager;

import com.yufan.library.inject.VuClass;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(SetRechargePasswordVu.class)
public class SetRechargePasswordFragment extends BaseFragment<SetRechargePasswordContract.IView> implements SetRechargePasswordContract.Presenter {



    @Override
    public void onRefresh() {

    }

    @Override
    public void hideSoftInput() {
        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
