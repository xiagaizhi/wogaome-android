package com.yushi.leke.fragment.openTreasureBox;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufan.library.inject.AnnotateUtils;
import com.yufan.library.util.PxUtil;
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
@FindLayout(layout = R.layout.fragment_layout_opentreasurebox)
@Title("开宝箱")
public class OpenTreasureBoxVu extends BaseVu<OpenTreasureBoxContract.Presenter> implements OpenTreasureBoxContract.IView, OpenTreasureBoxAdapter.OnItemClickListener {
    @FindView(R.id.id_recharge_container)
    RecyclerView id_recharge_container;
    @FindView(R.id.id_open_pay)
    Button id_open_pay;
    private OpenTreasureBoxAdapter mAdapter;


    @Override
    public void initView(View view) {
        id_open_pay.setOnClickListener(this);
        id_recharge_container.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new OpenTreasureBoxAdapter(mPersenter.getDatas(), getContext(), this);
        id_recharge_container.setAdapter(mAdapter);
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

        ImageView treasureBoxIcon = appToolbar.creatRightView(ImageView.class);
        treasureBoxIcon.setImageResource(R.drawable.ic_toolbar_treasure_box);
        treasureBoxIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.openTreasureBoxDetail();
            }
        });

        ImageView playerIcon = appToolbar.creatRightView(ImageView.class);
        playerIcon.setImageResource(R.drawable.ic_toolbar_player_white);
        playerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
    public RecyclerView getmRecyclerView() {
        return id_recharge_container;
    }

    @Override
    public void onItemClick(GoodsInfo goodsInfo) {
        mPersenter.selectGoodInfo(goodsInfo);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.id_open_pay:
                mPersenter.toPay();
                break;
        }
    }
}
