package com.yushi.leke.plugin.operation.login;

import android.view.View;

import com.yushi.leke.plugin.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_login)
@Title("登录")
public class LoginVu extends BaseVu<LoginContract.Presenter> implements LoginContract.View {
    @Override
    public void initView(View view) {

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return super.initTitle(appToolbar);
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
