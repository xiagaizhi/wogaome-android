package com.yushi.leke.dialog.update;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.inter.ICallBack;
import com.yushi.leke.YFApi;

/**
 * 作者：Created by zhanyangyang on 2018/9/13 09:17
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class UpdataManager {

    /**
     * 检查更新
     *
     * @param context
     */
    public static void checkAppUpdate(final Context context, final ICallBack iCallBack) {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).checkAppUpdate()).useCache(false).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                if (!TextUtils.isEmpty(mApiBean.getData())) {
                    UpdateInfo updateInfo = JSON.parseObject(mApiBean.getData(), UpdateInfo.class);
                    if (updateInfo != null) {
                        UpdateDialog updateDialog = new UpdateDialog(context, updateInfo, iCallBack);
                        updateDialog.show();
                    }
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
}
