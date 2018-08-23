package com.yushi.leke.fragment.openTreasureBox;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
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
    private OpenTreasureBoxAdapter mAdapter;

    @Override
    public void initView(View view) {
        id_recharge_container.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new OpenTreasureBoxAdapter(mPersenter.getDatas(), getContext());
        mAdapter.setOnItemClickListener(this);
        id_recharge_container.setAdapter(mAdapter);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        String titleNameStr = AnnotateUtils.getTitle(this);
        if (!TextUtils.isEmpty(titleNameStr)) {
            TextView titleName = appToolbar.creatCenterView(TextView.class);
            titleName.setText(titleNameStr);
        }
        appToolbar.creatLeftView(ImageView.class);
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
    public void onItemClick(int position) {

    }
}
