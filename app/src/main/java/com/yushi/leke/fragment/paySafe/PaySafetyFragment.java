package com.yushi.leke.fragment.paySafe;

import android.os.Bundle;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.DialogManager;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.util.RechargeUtil;
import com.yushi.leke.dialog.recharge.SetRechargePwdDialog;
import com.yushi.leke.fragment.bindPhone.BindPhoneFragment;
import com.yushi.leke.fragment.bindPhone.checkPhone.CheckPhoneFragment;

import org.json.JSONObject;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(PaySafetyVu.class)
public class PaySafetyFragment extends BaseFragment<PaySafetyContract.IView> implements PaySafetyContract.Presenter, RechargeUtil.SetRechargeInterf, RechargeUtil.CheckRechargePwdInterf {
    private int isHave;
    private String phoneNumber;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).haveTradePwd())
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        try {
                            String data = mApiBean.getData();
                            JSONObject jsonObject = new JSONObject(data);
                            isHave = jsonObject.optInt("isHave");
                            phoneNumber = jsonObject.optString("phoneNumber");
                            getVu().updatePage(isHave, phoneNumber);
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
    public void forgetPwd() {
        if (isHave == 0 || TextUtils.isEmpty(phoneNumber)) {//未绑定手机或者未设置交易密码 提示请先设置交易密码
            DialogManager.getInstance().toast("请先设置交易密码！");
        } else {//绑定过手机验证手机 通过后忘记交易密码
            startForResult(UIHelper.creat(CheckPhoneFragment.class).put("phoneNumber", phoneNumber).put("type", CheckPhoneFragment.CHECKPHONE_FROM_PAYSAFETY).build(), 200);
        }
    }

    @Override
    public void setRechargePwd() {
        if (TextUtils.isEmpty(phoneNumber)) {//未绑定手机，先绑定手机
            startForResult(UIHelper.creat(BindPhoneFragment.class).put("type", BindPhoneFragment.BINDPHOE_NEED_TOKEN).build(), 100);
        } else {
            if (isHave == 1) {//修改，先校验
                RechargeUtil.getInstance().checkRechargePwd(_mActivity, "修改交易密码", this);
            } else {
                startForResult(UIHelper.creat(CheckPhoneFragment.class).put("phoneNumber", phoneNumber).put("type", CheckPhoneFragment.CHECKPHONE_FROM_PAYSAFETY).build(), 300);
            }
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {//绑定手机成功返回
            phoneNumber = data.getString("phoneNumber");
            String token = data.getString("token");
            getVu().updatePage(isHave, phoneNumber);
            RechargeUtil.getInstance().setRechargePwd(_mActivity, token, null, SetRechargePwdDialog.SET_RECHARGE_PWD_BYTOKEN, this);
        } else if (requestCode == 200 && resultCode == RESULT_OK && data != null) {//手机验证码校验过返回,忘记密码
            String token = data.getString("token");
            RechargeUtil.getInstance().setRechargePwd(_mActivity, token, null, SetRechargePwdDialog.SET_RECHARGE_PWD_BYTOKEN, this);
        } else if (requestCode == 300 && resultCode == RESULT_OK && data != null) {//初次设置交易密码手机验证通过，设置密码
            String token = data.getString("token");
            RechargeUtil.getInstance().setRechargePwd(_mActivity, token, null, SetRechargePwdDialog.SET_RECHARGE_PWD_BYTOKEN, this);
        }
    }


    @Override
    public void returnSetPwdResult(boolean isSuccess) {
        if (isSuccess) {
            isHave = 1;
            getVu().updatePage(isHave, phoneNumber);
        }
    }

    @Override
    public void returnCheckResult(boolean isSuccess, String originalPwd) {
        if (isSuccess) {
            RechargeUtil.getInstance().setRechargePwd(_mActivity, null, originalPwd, SetRechargePwdDialog.SET_RECHARGE_PWD_BYOLDPWD, this);
        }
    }
}
