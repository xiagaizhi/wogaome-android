package com.yushi.leke.plugin.wxapi;

import android.os.Bundle;

import com.umeng.weixin.callback.WXCallbackActivity;


public class WXEntryActivity extends WXCallbackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        } catch (Exception e) {
            finish();
        }
    }
}
