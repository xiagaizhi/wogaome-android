package com.yushi.leke.fragment.exhibition.exhibitionHome;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.UIHelper;
import com.yushi.leke.dialog.ShareDialog;

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
    private float topHeightMax;
    private float topHeightMin;
    private ImageView musicAnim;
    private ImageView img_history;
    private float historyBottom;

    @Override
    public void initView(View view) {
        super.initView(view);
        topHeightMin = getContext().getResources().getDimension(R.dimen.y68);
        topHeightMax = getContext().getResources().getDimension(R.dimen.y98);
        historyBottom = getContext().getResources().getDimension(R.dimen.px158);
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
                    if (top_offset > topHeightMin) {
                        if (top_offset < topHeightMax) {
                            if (mTitleView != null) {
                                float offset = (top_offset - topHeightMin) / (topHeightMax - topHeightMin);
                                Log.d("offset", "appToolbar:" + topHeightMax);
                                mTitleView.setAlpha(offset);
                                musicAnim.setAlpha(offset);
                                img_history.setAlpha(offset);

                            }
                        } else {
                            mTitleView.setAlpha(1f);
                            musicAnim.setAlpha(1f);
                            img_history.setAlpha(1f);
                        }
                    } else {
                        mTitleView.setAlpha(0f);
                        musicAnim.setAlpha(0f);
                        img_history.setAlpha(0f);

                    }
                } else {
                    mTitleView.setAlpha(1f);
                    musicAnim.setAlpha(1f);
                    img_history.setAlpha(1f);
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
        this.appToolbar = appToolbar;
        img_history = appToolbar.creatRightView(ImageView.class);
        img_history.setImageResource(R.drawable.ic_history_exc);
        img_history.setAlpha(0f);

        mTitleView = appToolbar.creatCenterView(TextView.class);
        mTitleView.setText("路演厅");
        mTitleView.setAlpha(0);
        mTitleView.getPaint().setFakeBoldText(true);

        img_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.openPastActivities();
            }
        });
        appToolbar.build();
        musicAnim = UIHelper.getMusicView(mPersenter.getActivity(), appToolbar,ExhibitionVu.class);
        musicAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getAlpha() > 0) {
                    mPersenter.onMusicMenuClick();
                }
            }
        });
        return true;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
