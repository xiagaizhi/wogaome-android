package com.yushi.leke.fragment.roadShow;

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
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_roadshowhall)
@Title("路演大厅")
public class RoadshowHallVu extends BaseVu<RoadshowHallContract.Presenter> implements RoadshowHallContract.IView {
    @Override
    public void initView(View view) {

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        TextView titltView=  appToolbar.creatCenterView(TextView.class);
        titltView.setText("路演大厅");
        appToolbar.build();
        return true;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
