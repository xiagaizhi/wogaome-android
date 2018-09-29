package com.yushi.leke.fragment.setting.AccountAndSafety;

import android.os.Bundle;

import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.inject.VuClass;
import com.yufan.library.manager.UserManager;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.bindPhone.BindPhoneFragment;
import com.yushi.leke.fragment.bindPhone.checkPhone.CheckPhoneFragment;
import com.yushi.leke.fragment.bindPhone.updatePhone.UpdatePhoneFragment;
import com.yushi.leke.fragment.setting.modifyLoginPwd.ModifyLoginPwdFragment;
import com.yushi.leke.util.ArgsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(AccountAndSafetyVu.class)
public class AccountAndSafetyFragment extends BaseFragment<AccountAndSafetyContract.IView> implements AccountAndSafetyContract.Presenter {
    private String phoneNumber;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getMobile())
                .useCache(false)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        phoneNumber = mApiBean.getData();
                        getVu().updatePage(phoneNumber);
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
    public void openBindPhone() {
        if (TextUtils.isEmpty(phoneNumber)) {//未绑定手机
            startForResult(UIHelper.creat(BindPhoneFragment.class).put("type", BindPhoneFragment.BINDPHONE_NORMAL).build(), 100);
        } else {//绑定过手机，换绑手机，先校验之前手机
            startForResult(UIHelper.creat(CheckPhoneFragment.class).put("phoneNumber", phoneNumber).put("type", CheckPhoneFragment.CHECKPHONE_FROM_ACCOUNTSAFETY).build(), 200);
        }
    }

    @Override
    public void modifyPwd() {
        if (TextUtils.isEmpty(phoneNumber)) {
            DialogManager.getInstance().toast("请先绑定手机");
        } else {//修改密码
            start(UIHelper.creat(ModifyLoginPwdFragment.class).put("phoneNumber", phoneNumber).build());
        }
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {//绑定手机成功返回
            phoneNumber = data.getString("phoneNumber");
            getVu().updatePage(phoneNumber);
            //解绑手机数据埋点
            ArgsUtil.datapoint("0400","uid", UserManager.getInstance().getUid(),"phone",phoneNumber);
        } else if (requestCode == 200 && resultCode == RESULT_OK && data != null) {//手机验证码校验过通过，换绑
            String token = data.getString("token");
            startForResult(UIHelper.creat(UpdatePhoneFragment.class).put("token", token).build(), 100);
        }
    }
}
