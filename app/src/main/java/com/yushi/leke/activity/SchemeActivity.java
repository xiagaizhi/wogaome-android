package com.yushi.leke.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * 作者：Created by zhanyangyang on 2018/9/28 14:21
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class SchemeActivity extends Activity {
    //内部跳转格式："rbaction://module?param=value&"
    private static final String SCHEME_HOST = "module";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dispatchIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        dispatchIntent(intent);
    }

    private void dispatchIntent(Intent intent) {
        if (intent != null) {
            Uri uri = intent.getData();
            if (uri != null) {
                try {
                    final String host = uri.getAuthority();
                    if (TextUtils.equals(SCHEME_HOST, host)) {
                        final String method = uri.getQueryParameter("method");
                        if (TextUtils.equals(method, "update")) {
                        } else if (TextUtils.equals(method, "startGame")) {
                            String gamePkg = uri.getQueryParameter("gamePkg");
                            String gameName = uri.getQueryParameter("gameName");
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
        finish();
    }
}
