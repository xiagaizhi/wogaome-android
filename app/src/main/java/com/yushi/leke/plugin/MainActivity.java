package com.yushi.leke.plugin;

import android.os.Bundle;

import com.yufan.library.UIHelper;
import com.yufan.library.base.BaseActivity;
import com.yushi.leke.plugin.R;
import com.yushi.leke.plugin.operation.dbtest.TestListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       loadRootFragment(R.id.rl_fragment, UIHelper.creat(TestListFragment.class).build());//.put(Global.BUNDLE_KEY_BROWSER_URL,"http://www.baidu.com")
    }
}
