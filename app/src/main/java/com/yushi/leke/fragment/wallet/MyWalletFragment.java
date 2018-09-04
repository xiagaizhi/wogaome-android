package com.yushi.leke.fragment.wallet;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.yufan.library.browser.BrowserBaseFragment;
import com.yufan.library.inject.VuClass;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;
import com.yushi.leke.fragment.openTreasureBox.OpenTreasureBoxFragment;
import com.yushi.leke.fragment.paySafe.PaySafetyFragment;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(MyWalletVu.class)
public class MyWalletFragment extends BaseFragment<MyWalletContract.IView> implements MyWalletContract.Presenter {
    private MyWalletInfo myWalletInfo;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWalletInfo();
    }

    private void getWalletInfo() {
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).getWalletInfo())
                .useCache(true)
                .enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        String data = mApiBean.getData();
                        myWalletInfo = JSON.parseObject(data, MyWalletInfo.class);
                        getVu().upDataMyWallet(myWalletInfo);
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
    public void openPaySafety() {
        getRootFragment().start(UIHelper.creat(PaySafetyFragment.class).build());
    }

    @Override
    public void openPlayer() {
    }

    @Override
    public void openTreasureBox() {
        getRootFragment().startForResult(UIHelper.creat(OpenTreasureBoxFragment.class).build(), 100);
    }


    @Override
    public void openBrowserPage(String key) {
        if (TextUtils.equals(key,"lkc说明页")){
            start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://web.leke-dev.com/#/myWallet/LKCInstruction").build());
        }else if (TextUtils.equals(key,"lkc明细")){
            start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://web.leke-dev.com/#/myWallet/LKCDetail").build());
        }else if (TextUtils.equals(key,"昨日算力")){
            start(UIHelper.creat(BrowserBaseFragment.class).put(Global.BUNDLE_KEY_BROWSER_URL, "http://web.leke-dev.com/#/myPower").build());
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            // TODO: 2018/8/28 刷新界面 
        }
    }
}
