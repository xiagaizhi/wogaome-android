package com.yushi.leke.fragment.exhibition.fourpage;

import com.yushi.leke.R;

import android.view.View;
import android.widget.TextView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.uamp.playback.Playback;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.xx_doend_main)
public class fourpageVu extends BaseListVu<fourpageContract.Presenter> implements fourpageContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    TextView tv;
    @Override
    public void initView(View view) {
        super.initView(view);
        tv= (TextView) findViewById(R.id.tv_vote_checkall);
        tv.setClickable(true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.MyCallback();
            }
        });
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) { return false; }
    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
