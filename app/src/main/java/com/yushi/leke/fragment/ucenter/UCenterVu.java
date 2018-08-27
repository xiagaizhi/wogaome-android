package com.yushi.leke.fragment.ucenter;

import android.view.View;
import android.widget.TextView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_ucenter)
@Title("我的")
public class UCenterVu extends BaseVu<UCenterContract.Presenter> implements UCenterContract.IView {
    @Override
    public void initView(View view) {

    }


    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        TextView titltView=  appToolbar.creatCenterView(TextView.class);
        titltView.setText("我的");
        appToolbar.build();
        return true;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
