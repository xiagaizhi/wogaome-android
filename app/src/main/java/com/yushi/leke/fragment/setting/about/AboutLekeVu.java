package com.yushi.leke.fragment.setting.about;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.dialog.update.UpdateInfo;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_aboutleke)
@Title("关于乐客")
public class AboutLekeVu extends BaseVu<AboutLekeContract.Presenter> implements AboutLekeContract.IView {
    @FindView(R.id.btn_upgrade)
    Button btn_upgrade;
    @FindView(R.id.tv_new_tips)
    TextView tv_new_tips;

    @Override
    public void initView(View view) {
        btn_upgrade.setOnClickListener(this);
        //去检测
        btn_upgrade.setBackgroundResource(R.drawable.shape_bg_00b2ff_007afff);
        btn_upgrade.setTextColor(getContext().getResources().getColor(R.color.white));
        btn_upgrade.setText("检测新版本");
        //检测中
//        btn_upgrade.setTextColor(getContext().getResources().getColor(R.color.color_blue_level6));
//        btn_upgrade.setBackgroundResource(R.drawable.shape_bg_33007afff);
//        btn_upgrade.setText("检测中");
//        //检测完毕，最新版本
//        btn_upgrade.setBackgroundResource(R.drawable.shape_bg_eeeeee);
//        btn_upgrade.setTextColor(getContext().getResources().getColor(R.color.color_gray_level9));
//        btn_upgrade.setText("已是最新版本");
    }


    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        super.initTitle(appToolbar);
        ImageView player = appToolbar.creatRightView(ImageView.class);
        player.setImageResource(R.drawable.ic_toolbar_player_blue);
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.openPlayer();
            }
        });
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
            case R.id.btn_upgrade:
                if (TextUtils.equals("检测新版本", btn_upgrade.getText().toString())) {
                    btn_upgrade.setEnabled(false);
                    btn_upgrade.setTextColor(getContext().getResources().getColor(R.color.color_blue_level6));
                    btn_upgrade.setBackgroundResource(R.drawable.shape_bg_33007afff);
                    btn_upgrade.setText("检测中...");
                    mPersenter.checkUpdate();
                } else {
                    mPersenter.toUpgrade();
                }
                break;
        }
    }

    @Override
    public void returnUpdateInfo(UpdateInfo updateInfo) {
        if (updateInfo != null && updateInfo.isNeedUpdate()) {
            btn_upgrade.setEnabled(true);
            btn_upgrade.setBackgroundResource(R.drawable.shape_bg_00b2ff_007afff);
            btn_upgrade.setTextColor(getContext().getResources().getColor(R.color.white));
            btn_upgrade.setText("版本" + updateInfo.getAppVersion());
            tv_new_tips.setVisibility(View.VISIBLE);
        } else {
            btn_upgrade.setEnabled(false);
            btn_upgrade.setBackgroundResource(R.drawable.shape_bg_eeeeee);
            btn_upgrade.setTextColor(getContext().getResources().getColor(R.color.color_gray_level9));
            btn_upgrade.setText("已是最新版本");
            tv_new_tips.setVisibility(View.GONE);
        }
    }
}
