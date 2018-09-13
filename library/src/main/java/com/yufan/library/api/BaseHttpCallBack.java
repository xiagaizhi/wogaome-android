package com.yufan.library.api;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yufan.library.Global;
import com.yufan.library.base.BaseApplication;
import com.yufan.library.base.BaseVu;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.view.recycler.PageInfo;


/**
 * Created by mengfantao on 18/2/27.
 */

public abstract class BaseHttpCallBack implements IHttpCallBack {


    public abstract void onSuccess(ApiBean mApiBean);

    public abstract void onError(int id, Exception e);

    public abstract void onFinish();

    private BaseVu vu;

    public BaseHttpCallBack(BaseVu vu) {
        this.vu = vu;
    }

    public BaseHttpCallBack() {
    }

    @Override
    public void onResponse(ApiBean mApiBean) {
        if (ApiBean.checkOK(mApiBean.getCode())) {
            if (vu != null) {
                vu.setStateGone();
            }

            onSuccess(mApiBean);
        } else if (TextUtils.equals(ApiBean.TOKEN_LOSE, mApiBean.getCode())) {
            //登录
            Intent filter = new Intent();
            filter.setAction(Global.BROADCAST_TOKEN_LOSE);
            LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(filter);
        } else {
            DialogManager.getInstance().toast(mApiBean.message);
        }
        onFinish();
    }

    @Override
    public void onFailure(int id, Exception e) {
        DialogManager.getInstance().toast(e.getMessage());
        if (vu != null) {
            vu.setStateError();
        }
        onError(id, e);
        onFinish();
    }
}
