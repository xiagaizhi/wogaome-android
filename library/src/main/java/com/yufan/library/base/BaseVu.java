package com.yufan.library.base;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yufan.library.R;
import com.yufan.library.inject.AnnotateUtils;
import com.yufan.library.util.Netutil;
import com.yufan.library.util.PxUtil;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.widget.StateLayout;


/**
 * Created by mengfantao on 18/6/28.
 * vu view模块基础类,
 */

public abstract class BaseVu<T extends Pr> implements Vu ,View.OnClickListener{
    protected RelativeLayout mRootLayout;
    protected View mContentLayout;
    protected StateLayout mStateLayout;
    protected AppToolbar mToolbarLayout;
    private Context mContext;
    protected T mPersenter;

    @Override
    public T getPresenter() {
        return mPersenter;
    }

    @Override
    public void setPresenter(Object presenter) {
        mPersenter = (T) presenter;
    }

    @Override
    public final View getView() {
        return mRootLayout;
    }

    public final Context getContext() {
        return mContext;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public final void init(LayoutInflater inflater, ViewGroup container) {
        this.mContext = inflater.getContext();
        mRootLayout = new RelativeLayout(mContext);
        mRootLayout.setId(R.id.root_content_id);
        int layoutId = AnnotateUtils.getLayoutId(this);
        if (layoutId == 0) {
            throw new IllegalArgumentException("加入类注解 @FindLayout(layout = R.layout.layout_fragment_list,statusLayoutParent = R.id.rl_content)");
        }
        mContentLayout = inflater.inflate(layoutId, container, false);
        mToolbarLayout = new AppToolbar(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRootLayout.addView(mContentLayout, layoutParams);
        addTitle(mToolbarLayout, initTitle(mToolbarLayout));
        initState();
        AnnotateUtils.injectViews(this);
        initView(mRootLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        String titleNameStr = AnnotateUtils.getTitle(this);
        if (!TextUtils.isEmpty(titleNameStr)) {
            TextView titleName = appToolbar.creatCenterView(TextView.class);
            titleName.setText(titleNameStr);
        }
        ImageView leftView=  appToolbar.creatLeftView(ImageView.class);
        leftView.setImageResource(R.drawable.left_back_black_arrows);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.onBackPressed();
            }
        });
        appToolbar.build();
        return true;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        if (TextUtils.isEmpty(Netutil.GetNetworkType(BaseApplication.getInstance()))) {
            setStateError();
        }else {
            setStateEmpty();
        }
        stateLayout.getEmptyView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.onRefresh();
            }
        });
        stateLayout.getErrorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.onRefresh();
            }
        });
    }

    protected void resetTitle(AppToolbar appToolbar){
        mRootLayout.removeView(appToolbar);
        addTitle(appToolbar,true);
    }

    /**
     * 添加头
     */
    private final void addTitle(AppToolbar appToolbar, boolean isShowTitle) {
        if (isShowTitle) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, PxUtil.convertDIP2PX(mContext, 44));
            mRootLayout.addView(appToolbar, lp);
            if (appToolbar.isVertical()) {
               // appToolbar.getBackgroundView().setBackgroundResource(R.drawable.shape_title_backgound);
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mContentLayout.getLayoutParams();
                rlp.addRule(RelativeLayout.BELOW, R.id.title_id);
                mContentLayout.setLayoutParams(rlp);
            } else {
               // appToolbar.getBackgroundView().setBackgroundResource(R.drawable.shape_title_backgound);
                appToolbar.getBackgroundView().setAlpha(0);
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) mContentLayout.getLayoutParams();
                rlp.removeRule(RelativeLayout.BELOW);
            }
        }

    }

    /**
     * 初始化状态view
     */
    protected void initState() {
        mStateLayout = new StateLayout(this);
        initStatusLayout(mStateLayout);
        int stateParentId = AnnotateUtils.getStateParentId(this);
        if (stateParentId != 0) {
            ViewGroup stateViewGroup = mRootLayout.findViewById(stateParentId);
            if (stateViewGroup != null) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                stateViewGroup.addView(mStateLayout, layoutParams);
            }
            mStateLayout.setVisibility(View.GONE);
        }
    }

    public final void setStateGone() {
        mStateLayout.setVisibility(View.GONE);
    }

    public  void setStateError() {


        mStateLayout.setStateError();
    }

    public  void setStateEmpty() {
        mStateLayout.setStateEmpty();
    }

    public final View findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }

}
