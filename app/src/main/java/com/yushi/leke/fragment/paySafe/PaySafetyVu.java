package com.yushi.leke.fragment.paySafe;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.base.BasePopupWindow;
import com.yufan.library.inject.FindView;
import com.yufan.library.widget.highlightview.HighLightInfo;
import com.yufan.library.widget.highlightview.HighLightView;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_paysafety)
@Title("支付安全")
public class PaySafetyVu extends BaseVu<PaySafetyContract.Presenter> implements PaySafetyContract.IView {
    @FindView(R.id.rl_setpwd)
    View rl_setpwd;
    @FindView(R.id.tv_setpwd)
    TextView tv_setpwd;
    @FindView(R.id.tv_tips)
    TextView tv_tips;

    @FindView(R.id.rl_forget_pwd)
    View rl_forget_pwd;
    private int id;
    private HighLightView highLightView;
    private ImageView playerIcon;

    @Override
    public void initView(View view) {
        rl_setpwd.setOnClickListener(this);
        rl_forget_pwd.setOnClickListener(this);
        BasePopupWindow popupWindow = new BasePopupWindow(getContext());
        highLightView = new HighLightView(getContext(), popupWindow);
        HighLightInfo highLightInfo = new HighLightInfo(playerIcon, R.drawable.jt_up, HighLightInfo.ALIGN_LEFT_DOWN, HighLightInfo.HEIGHTLIGHT_CIR);
        highLightView.addHighLightInfo(0, highLightInfo);
        popupWindow.addView(highLightView);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, 0);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        super.initTitle(appToolbar);
        playerIcon = appToolbar.creatRightView(ImageView.class);
        playerIcon.setImageResource(R.drawable.ic_toolbar_player_blue);
        id = View.generateViewId();
        playerIcon.setId(id);
        appToolbar.build();
        return true;
    }


    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_setpwd://设置密码／修改密码
                break;
            case R.id.rl_forget_pwd://忘记密码
                break;
        }
    }
}
