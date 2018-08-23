package com.yushi.leke.fragment.wallet;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.inject.AnnotateUtils;
import com.yufan.library.inject.FindView;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_mywallet)
@Title("我的钱包")
public class MyWalletVu extends BaseVu<MyWalletContract.Presenter> implements MyWalletContract.IView {
    @FindView(R.id.id_lck_instructions)
    ImageView id_lck_instructions;
    @FindView(R.id.id_lkc_remain)
    TextView id_lkc_remain;
    @FindView(R.id.id_lkc_detail)
    TextView id_lkc_detail;
    @FindView(R.id.id_yesterd_arith_num)
    TextView id_yesterd_arith_num;
    @FindView(R.id.id_open_treasure)
    Button id_open_treasure;

    @Override
    public void initView(View view) {
        id_lkc_remain.setText(Html.fromHtml("<b><big><font color=#151515;size=60px>" + "48321." + "</font></big></b>"
                + "<font color=#333333;size=60px>" + "442332" + "</font>"));
        id_lck_instructions.setOnClickListener(this);
        id_lkc_detail.setOnClickListener(this);
        id_yesterd_arith_num.setOnClickListener(this);
        id_open_treasure.setOnClickListener(this);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        String titleNameStr = AnnotateUtils.getTitle(this);
        if (!TextUtils.isEmpty(titleNameStr)) {
            TextView titleName = appToolbar.creatCenterView(TextView.class);
            titleName.setText(titleNameStr);
        }
        ImageView leftView = appToolbar.creatLeftView(ImageView.class);
        leftView.setImageResource(com.yufan.library.R.drawable.left_back_white_arrows);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.onBackPressed();
            }
        });

        ImageView keyIcon = appToolbar.creatRightView(ImageView.class);
        keyIcon.setImageResource(R.drawable.ic_toolbar_key);
        keyIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.openPaySafety();
            }
        });

        ImageView playerIcon = appToolbar.creatRightView(ImageView.class);
        playerIcon.setImageResource(R.drawable.ic_toolbar_player_white);
        playerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.openPlayer();
            }
        });
        appToolbar.build(false);
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
            case R.id.id_lck_instructions://lkc说明页
                break;
            case R.id.id_lkc_detail://lkc明细
                break;
            case R.id.id_yesterd_arith_num://昨日算力
                break;
            case R.id.id_open_treasure://点击开宝箱
                break;
        }
    }
}
