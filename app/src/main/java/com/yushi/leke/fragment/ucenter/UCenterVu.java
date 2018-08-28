package com.yushi.leke.fragment.ucenter;

import android.view.View;
import android.widget.Button;
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
    @FindView(R.id.bt_1)
    Button button;
    @FindView(R.id.bt_mywallet)
    Button bt_mywallet;
    @Override
    public void initView(View view) {
button.setOnClickListener(this);
        bt_mywallet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_1:
                mPersenter.startPlayer();

                break;
            case R.id.bt_mywallet:
                mPersenter.openMyWallet();
                break;
        }
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
