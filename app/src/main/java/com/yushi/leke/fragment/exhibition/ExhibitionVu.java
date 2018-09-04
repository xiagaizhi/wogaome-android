package com.yushi.leke.fragment.exhibition;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yushi.leke.R;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.layout_fragment_list)
@Title("路演厅")
public class ExhibitionVu extends BaseListVu<ExhibitionContract.Presenter> implements ExhibitionContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    private AppToolbar appToolbar;
    private TextView mTitleView;
    private float topHeight;

    @Override
    public void initView(View view) {
        super.initView(view);
        topHeight=getContext().getResources().getDimension(R.dimen.px88);
        mYFRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (lastVisibleItemPosition == 0) {
                    View topChild = layoutManager.getChildAt(0);
                    float top_offset = -topChild.getTop();
                    if(top_offset<topHeight){
                        if(mTitleView!=null){
                            float offset=   top_offset/topHeight;
                            Log.d("offset","appToolbar:"+offset);
                            mTitleView.setAlpha( offset);
                        }
                    }else {
                        mTitleView.setAlpha( 1);
                    }
                }else {
                    mTitleView.setAlpha( 1);
                }
            }
        });
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        this.appToolbar=appToolbar;
        mTitleView=     appToolbar.creatCenterView(TextView.class);
        mTitleView.setText("路演厅");
        mTitleView.setAlpha(0);
        appToolbar.build();
        return true;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
