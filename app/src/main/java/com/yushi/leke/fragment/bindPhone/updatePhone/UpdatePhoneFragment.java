package com.yushi.leke.fragment.bindPhone.updatePhone;

import android.os.Bundle;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.manager.DialogManager;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.CommonDialog;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(UpdatePhoneVu.class)
public class UpdatePhoneFragment extends BaseFragment<UpdatePhoneContract.IView> implements UpdatePhoneContract.Presenter {
    private String token;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            token = bundle.getString("token");
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void getVerifcationCode(String sessionId, String phone) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).sendBindNewMobileVcode(sessionId, phone))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {

                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    @Override
    public void updatePhone(final String phone, String code) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).changeMobile(phone, code, token))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onResponse(ApiBean mApiBean) {
                        if (ApiBean.checkOK(mApiBean.getCode())) {
                            Bundle bundle = new Bundle();
                            bundle.putString("phoneNumber", phone);
                            setFragmentResult(RESULT_OK, bundle);
                            DialogManager.getInstance().toast("换绑成功！");
                            pop();
                        } else {
                            new CommonDialog(_mActivity)
                                    .setTitle(mApiBean.getMessage())
                                    .setNegativeName("取消")
                                    .setPositiveName("确定")
                                    .setCommonClickListener(new CommonDialog.CommonDialogClick() {
                                        @Override
                                        public void onClick(CommonDialog commonDialog, int actionType) {
                                            commonDialog.dismiss();
                                        }
                                    }).show();
                        }
                    }

                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });

    }
}
