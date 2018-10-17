package com.yushi.leke.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yufan.library.Global;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxa78cb5eacb190d7f");
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Intent filter = new Intent();
        if (resp.errCode == 0) {//成功
            filter.putExtra(Global.INTENT_PAY_RESUIL_DATA, true);
        } else {//失败
            filter.putExtra(Global.INTENT_PAY_RESUIL_DATA, false);
        }
        filter.setAction(Global.BROADCAST_PAY_RESUIL_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(filter);
        finish();
    }
}