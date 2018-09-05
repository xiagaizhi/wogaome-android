package com.yushi.leke.fragment.home;

import com.yufan.library.util.PxUtil;
import com.yushi.leke.R;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.UIHelper;
import com.yushi.leke.activity.MusicPlayerActivity;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.layout_fragment_list)
@Title("订阅专栏")
public class SubscriptionsVu extends BaseListVu<SubscriptionsContract.Presenter> implements SubscriptionsContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    private float topHeightMax;
    private float topHeightMin;
    private float searchBarBottom;
    private TextView mTitleView;
    private AppToolbar appToolbar;
    private ImageView musicAnim;
    private ImageView searchBar;

    @Override
    public void initView(View view) {
        super.initView(view);
        topHeightMin = getContext().getResources().getDimension(R.dimen.px88);
        topHeightMax = topHeightMin * 3 / 2;
        searchBarBottom=getContext().getResources().getDimension(R.dimen.px170);
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
                                float offset = (top_offset - topHeightMin) / (topHeightMax-topHeightMin);
                                Log.d("offset", "appToolbar:" + topHeightMin);
                                mTitleView.setAlpha(offset);
                                musicAnim.setAlpha(offset);
                            }
                        }else {
                            mTitleView.setAlpha(1f);
                            musicAnim.setAlpha(1f);
                        }
                    } else {
                        mTitleView.setAlpha(0f);
                        musicAnim.setAlpha(0f);
                    }
                    if(top_offset>searchBarBottom){
                        float offset=(top_offset-searchBarBottom)/(topHeightMax-topHeightMin);
                        searchBar.setAlpha(offset);
                    }else {
                        searchBar.setAlpha(0f);
                    }
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
        mTitleView = appToolbar.creatCenterView(TextView.class);
        searchBar = appToolbar.creatRightView(ImageView.class);
        musicAnim = appToolbar.creatRightView(ImageView.class);
        searchBar.setImageResource(R.drawable.ic_search_blue);
        musicAnim.setImageResource(R.drawable.anim_player_blue);
        musicAnim.setPadding(0, 0, (int) getContext().getResources().getDimension(R.dimen.px36), 0);
        ((AnimationDrawable) musicAnim.getDrawable()).start();
        mTitleView.setText("订阅专栏");
        mTitleView.setAlpha(0f);
        mTitleView .getPaint().setFakeBoldText(true);
        searchBar.setAlpha(0f);
        appToolbar.build();
        return true;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
