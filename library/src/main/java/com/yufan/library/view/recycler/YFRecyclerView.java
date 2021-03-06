package com.yufan.library.view.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.yufan.library.R;
import com.yufan.library.inter.EndlessRecyclerOnScrollListener;
import com.yufan.library.view.ptr.PtrClassicFrameLayout;
import com.yufan.library.view.ptr.PtrDefaultHandler;
import com.yufan.library.view.ptr.PtrFrameLayout;
import com.yufan.library.view.ptr.PtrHandler;

import java.util.List;

/**
 * Created by mengfantao on 17/5/16.
 * 列表模块
 */

public class YFRecyclerView extends WrapRecyclerView {
    private  boolean needLoadMore;
    protected LoadMoreModule mLoadMoreModule;
    protected PageInfo mPageInfo;
    protected OnPagerListener mOnPagerListener;
    private OnVerticalOffsetChange onVerticalOffsetChange;

    public PtrClassicFrameLayout ptrClassicFrameLayout;
    public void setOnVerticalOffsetChange(OnVerticalOffsetChange onVerticalOffsetChange) {
        this.onVerticalOffsetChange = onVerticalOffsetChange;
    }


    public void setOnPagerListener(OnPagerListener mOnPagerListener) {
        this.mOnPagerListener = mOnPagerListener;
    }

    public PageInfo getPageManager() {
        return mPageInfo;
    }

    public LoadMoreModule getmLoadMoreModule() {
        return mLoadMoreModule;
    }

    public YFRecyclerView(Context context) {
        this(context, null);
    }

    public YFRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YFRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.YFRecyclerView);
        needLoadMore = mTypedArray.getBoolean(R.styleable.YFRecyclerView_needLoadMore,true);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPageInfo = new PageInfo();
        initRecyclerview();

    }
    public PtrClassicFrameLayout getPTR(){
        return ptrClassicFrameLayout;
    }

    public void initPTR() {
        if (getParent() != null && getParent() instanceof PtrClassicFrameLayout) {
            ptrClassicFrameLayout= (PtrClassicFrameLayout) getParent();
            initPTR(ptrClassicFrameLayout);
        }
    }

    /**
     * 初始化Recyclerview
     */
    protected final void initRecyclerview() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        //recylerView.setHasFixedSize(true);
        if(needLoadMore){
            initLoadMore();
        }

    }
    public void setNeedLoadMore(boolean needLoadMore){

        if(needLoadMore&& this.needLoadMore){
            addOnScrollListener(listener);

        }else{
            removeOnScrollListener(listener);
        }

    }

    /**
     * 初始化上啦加载更多
     *
     * @param
     */
    protected final void initLoadMore() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mLoadMoreModule = new LoadMoreModule(getContext(), mPageInfo);
        mLoadMoreModule.getFootView().setLayoutParams(params);
        addFootView(mLoadMoreModule.getFootView());
        mLoadMoreModule.getFootView().setVisibility(View.GONE);
        addOnScrollListener(listener);

    }



    private EndlessRecyclerOnScrollListener listener= new EndlessRecyclerOnScrollListener() {
        @Override
        public void onHalf(View view) {
            mLoadMoreModule.getFootView().setVisibility(View.GONE);
        }

        @Override
        public void onLoadNextPage(View view) {
            mLoadMoreModule.getFootView().setVisibility(View.VISIBLE);
            if(mPageInfo.getmState()== PageInfo.PAGE_STATE_ERROR){
                mOnPagerListener.onLoadMore(mPageInfo.getCurrentIndex());
            }else if(mPageInfo.getmState()== PageInfo.PAGE_STATE_NONE){
                mOnPagerListener.onLoadMore(mPageInfo.getCurrentIndex());
            }

        }
    };
    /**
     * 初始化刷新
     *
     * @param mPtrFrame
     */
    protected void initPTR(PtrClassicFrameLayout mPtrFrame) {
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //处理刷新逻辑
                if (mPageInfo.isIdle()) {
                    mPageInfo.resetIndex();
                    mOnPagerListener.onRefresh();
                }

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if(onVerticalOffsetChange!=null){
                    return onVerticalOffsetChange.getOffset()==0&& PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }else{
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }

                //
            }
        });

    }


    public interface OnPagerListener {
        void onLoadMore(int index);

        void onRefresh();
    }
    public interface OnVerticalOffsetChange{
        int getOffset();
    }

}
