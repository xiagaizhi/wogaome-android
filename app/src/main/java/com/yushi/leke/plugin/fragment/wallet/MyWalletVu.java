package com.yushi.leke.plugin.fragment.wallet;

import android.view.View;
import android.widget.TextView;

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
@FindLayout(layout = R.layout.fragment_layout_mywallet)
@Title("我的钱包")
public class MyWalletVu extends BaseVu<MyWalletContract.Presenter> implements MyWalletContract.IView {
    @Override
    public void initView(View view) {

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {

        TextView setRechargePwd=  appToolbar.creatRightView(TextView.class);
        setRechargePwd.setText("设置交易密码");
        setRechargePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.setRechargePwd();
            }
        });

//        appToolbar.build();
        return super.initTitle(appToolbar);
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
