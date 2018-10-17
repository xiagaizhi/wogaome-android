package com.yufan.library.widget.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mengfantao on 17/5/6.
 */

public final class FlingBehavior extends AppBarLayout.Behavior {


    public FlingBehavior() {
    }

    public FlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, float velocityX, float velocityY, boolean consumed) {
        if (target instanceof RecyclerView) {
            final RecyclerView recyclerView = (RecyclerView) target;
            consumed = velocityY>0 ||recyclerView.computeVerticalScrollOffset() >0;
        }

        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

}