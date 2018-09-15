package com.yushi.leke.fragment.setting.about;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yushi.leke.R;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.base.BaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.YFApi;
import com.yushi.leke.dialog.update.UpdateInfo;
import com.yushi.leke.dialog.update.UpgradeUtil;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@VuClass(AboutLekeVu.class)
public class AboutLekeFragment extends BaseFragment<AboutLekeContract.IView> implements AboutLekeContract.Presenter {
    private UpdateInfo updateInfo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 检查更新
     */
    private void checkAppUpdate() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).checkAppUpdate()).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    updateInfo = JSON.parseObject(mApiBean.getData(), UpdateInfo.class);
                }
            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {
                getVu().returnUpdateInfo(updateInfo);
            }
        });
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void openPlayer() {

    }

    @Override
    public void checkUpdate() {
        checkAppUpdate();
    }

    @Override
    public void toUpgrade() {
        if (updateInfo != null && updateInfo.isNeedUpdate()) {
            UpgradeUtil.upgrade(_mActivity,updateInfo.getPkgPath());
        }
    }
}
