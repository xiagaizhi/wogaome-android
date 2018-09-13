package com.yushi.leke.fragment.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yufan.library.util.YFUtil;
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
@FindLayout(layout = R.layout.fragment_layout_setting)
@Title("设置")
public class SettingVu extends BaseVu<SettingContract.Presenter> implements SettingContract.IView {
    @FindView(R.id.rl_account_safety)
    RelativeLayout rl_account_safety;
    @FindView(R.id.rl_leke_about)
    RelativeLayout rl_leke_about;
    @FindView(R.id.rl_clear_cache)
    RelativeLayout rl_clear_cache;
    @FindView(R.id.tv_memory_cache)
    TextView tv_memory_cache;
    @FindView(R.id.rl_current_version)
    RelativeLayout rl_current_version;
    @FindView(R.id.tv_current_version)
    TextView tv_current_version;
    @FindView(R.id.rl_logout)
    RelativeLayout rl_logout;


    @Override
    public void initView(View view) {
        rl_account_safety.setOnClickListener(this);
        rl_leke_about.setOnClickListener(this);
        rl_clear_cache.setOnClickListener(this);
        rl_current_version.setOnClickListener(this);
        rl_logout.setOnClickListener(this);
        tv_current_version.setText(YFUtil.getVersionName());
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
    public void updataCacheSize(String cacheSize) {
        tv_memory_cache.setText(cacheSize);
    }

    @Override
    public void upDateVersion(UpdateInfo updateInfo) {
        if (updateInfo != null && updateInfo.isNeedUpdate()) {
            tv_current_version.setText(YFUtil.getVersionName() + "(发现新版本)");
        } else {
            tv_current_version.setText(YFUtil.getVersionName() + "(最新版本)");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_account_safety:
                mPersenter.accountAndSafety();
                break;
            case R.id.rl_leke_about:
                mPersenter.lekeAbout();
                break;
            case R.id.rl_clear_cache:
                mPersenter.cleanMemoryCache();
                break;
            case R.id.rl_current_version:
                mPersenter.upgrade();
                break;
            case R.id.rl_logout:
                mPersenter.logout();
                break;
        }
    }
}
