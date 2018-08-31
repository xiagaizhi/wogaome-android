package com.yushi.leke.fragment.paySafe;

import android.os.Bundle;

import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.recharge.CheckRechargePwdDialog;
import com.yushi.leke.dialog.recharge.SetRechargePwdDialog;
import com.yushi.leke.fragment.bindPhone.BindPhoneFragment;
import com.yushi.leke.fragment.bindPhone.checkPhone.CheckPhoneFragment;

import org.json.JSONObject;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(PaySafetyVu.class)
public class PaySafetyFragment extends BaseFragment<PaySafetyContract.IView> implements PaySafetyContract.Presenter, SetRechargePwdDialog.SetRechargeInterf, CheckRechargePwdDialog.CheckRechargePwdInterf {
    private int isHave;
    private String phoneNumber;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).haveTradePwd("v1", "9999"))
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        try {
                            if (TextUtils.equals(ApiBean.SUCCESS, mApiBean.getCode())) {
                                String data = mApiBean.getData();
                                JSONObject jsonObject = new JSONObject(data);
                                isHave = jsonObject.optInt("isHave");
                                phoneNumber = jsonObject.optString("phoneNumber");
                                getVu().updatePage(isHave, phoneNumber);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
    public void onRefresh() {

    }

    @Override
    public void openBindPhone(int type) {
        startForResult(UIHelper.creat(BindPhoneFragment.class).put(Global.BIND_PHONE_TYPE_KEY, type).build(), 100);
    }

    @Override
    public void checkPhone(String phoneNumber) {
        startForResult(UIHelper.creat(CheckPhoneFragment.class).put("phoneNumber", phoneNumber).build(), 200);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {//绑定手机成功返回
            phoneNumber = data.getString("phoneNumber");
            getVu().updatePage(isHave, phoneNumber);
            int bindPhoneType = data.getInt(Global.BIND_PHONE_TYPE_KEY, 0);
            if (bindPhoneType == Global.BIND_PHONE_FROM_SETPWD) {
                setRechargePwd(isHave);
            } else if (bindPhoneType == Global.BIND_PHONE_FROM_FORGETPWD) {
                setRechargePwd(isHave);
            }
        } else if (requestCode == 200 && resultCode == RESULT_OK && data != null) {//手机验证码校验过返回
            SetRechargePwdDialog setRechargePwdDialog = new SetRechargePwdDialog(getContext(), SetRechargePwdDialog.SET_RECHARGE_PWD_FORGET);
            setRechargePwdDialog.show();
        }
    }

    @Override
    public void setRechargePwd(int isHavePwd) {
        if (isHavePwd == 1) {//修改，先校验
            CheckRechargePwdDialog checkRechargePwdDialog = new CheckRechargePwdDialog(getContext(), CheckRechargePwdDialog.CHECK_RECHARGE_PWD_MODIFY, this);
            checkRechargePwdDialog.show();
        } else {
            SetRechargePwdDialog setRechargePwdDialog = new SetRechargePwdDialog(getContext(), SetRechargePwdDialog.SET_RECHARGE_PWD);
            setRechargePwdDialog.setmSetRechargeInterf(this);
            setRechargePwdDialog.show();
        }

    }

    @Override
    public void returnSetPwdResult() {
        isHave = 1;
        getVu().updatePage(isHave, phoneNumber);
    }

    @Override
    public void returnCheckResult() {
        SetRechargePwdDialog setRechargePwdDialog = new SetRechargePwdDialog(getContext(), SetRechargePwdDialog.SET_RECHARGE_PWD_NEW);
        setRechargePwdDialog.show();
    }
}
