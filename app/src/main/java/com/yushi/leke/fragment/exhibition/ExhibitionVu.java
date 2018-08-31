package com.yushi.leke.fragment.exhibition;

import com.yushi.leke.R;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.layout_fragment_list)
@Title("路演厅")
public class ExhibitionVu extends BaseListVu<ExhibitionContract.Presenter> implements ExhibitionContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;



    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        appToolbar.build();
        return true;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
