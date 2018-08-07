package com.yufan.library.view.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by mengfantao on 17/4/10.
 */


public class RecyclerWrapAdapter extends RecyclerView.Adapter implements WrapperAdapter {
    public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;
    private RecyclerView.Adapter mAdapter;

    private ArrayList<View> mHeaderViews;

    private ArrayList<View> mFootViews;

    private View emptyView;
    static final ArrayList<View> EMPTY_INFO_LIST =
            new ArrayList<View>();

    private int mCurrentPosition;

    private boolean isEmpty()
    {
        return emptyView!=null&&mAdapter.getItemCount() == 0;
    }
    public RecyclerWrapAdapter(View emptyView,ArrayList<View> mHeaderViews, ArrayList<View> mFootViews, RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        this.emptyView=emptyView;
        if (mHeaderViews == null) {
            this.mHeaderViews = EMPTY_INFO_LIST;
        } else {
            this.mHeaderViews = mHeaderViews;
        }
        if (mFootViews == null) {
            this.mFootViews = EMPTY_INFO_LIST;
        } else {
            this.mFootViews = mFootViews;
        }
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(isEmpty()){
            return new HeaderViewHolder(emptyView);
        }else {
            if (viewType<=-2000) {
                return new HeaderViewHolder(mFootViews.get(Math.abs(viewType+2000)));
            } else if (viewType<=-1000) {
                return new HeaderViewHolder(mHeaderViews.get(Math.abs(viewType+1000)));
            }
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmpty())
        {
            return;
        }
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return;
        }
        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(isEmpty()){
            return 1;
        }
        if (mAdapter != null) {
            return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
        } else {
            return getHeadersCount() + getFootersCount();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isEmpty())
                {
                    return gridLayoutManager.getSpanCount();
                }
                if (oldLookup != null)
                {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });


    }
    @Override
    public int getItemViewType(int position) {
        if (isEmpty())
        {
            return ITEM_TYPE_EMPTY;
        }
        mCurrentPosition = position;
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return -position-1000;//头
        }
        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        return -(adjPosition-adapterCount)-2000;//尾
    }


    @Override
    public long getItemId(int position) {
        int numHeaders = getHeadersCount();
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemId(adjPosition);
            }
        }
        return -1;
    }


    @Override
    public RecyclerView.Adapter getWrappedAdapter() {
        return mAdapter;
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mAdapter.onViewAttachedToWindow(holder);
        if (isEmpty())
        {
            WrapperUtils.setFullSpan(holder);
        }
    }

}