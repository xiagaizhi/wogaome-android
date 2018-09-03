package com.yushi.leke.fragment.setting.AccountAndSafety;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.util.StringUtil;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_accountandsafety)
@Title("账户与安全")
public class AccountAndSafetyVu extends BaseVu<AccountAndSafetyContract.Presenter> implements AccountAndSafetyContract.IView {
    @FindView(R.id.rl_bind_phone)
    RelativeLayout rl_bind_phone;
    @FindView(R.id.tv_bind_phone)
    TextView tv_bind_phone;
    @FindView(R.id.rl_modify_pwd)
    RelativeLayout rl_modify_pwd;
    @FindView(R.id.tv_modify_pwd)
    TextView tv_modify_pwd;

    @Override
    public void initView(View view) {
        rl_bind_phone.setOnClickListener(this);
        rl_modify_pwd.setOnClickListener(this);

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return super.initTitle(appToolbar);
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_bind_phone:
                mPersenter.openBindPhone();
                break;
            case R.id.rl_modify_pwd:
                break;
        }
    }

    @Override
    public void updatePage(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            tv_bind_phone.setText(StringUtil.handlePhoneNumber(phone));
        } else {
            tv_bind_phone.setText("未绑定");
        }
    }
}
