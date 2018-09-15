package com.yushi.leke.fragment.exhibition;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

    @Override
    public void initView(View view) {
        super.initView(view);
        topHeightMin = getContext().getResources().getDimension(R.dimen.px68);
        topHeightMax=getContext().getResources().getDimension(R.dimen.px98);
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
                                Log.d("offset", "appToolbar:" + topHeightMax);
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
                }else {
                    mTitleView.setAlpha(1f);
                    musicAnim.setAlpha(1f);
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
        musicAnim=  appToolbar.creatRightView(ImageView.class);
        musicAnim.setImageResource(R.drawable.anim_player_blue);
        ((AnimationDrawable) musicAnim.getDrawable()).start();
        mTitleView.setText("路演厅");
        mTitleView.setAlpha(0);
        mTitleView .getPaint().setFakeBoldText(true);
        musicAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getAlpha()>0){
                    mPersenter.onMusicMenuClick();
                }
            }
        });
        appToolbar.build();
        return true;
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }
}
