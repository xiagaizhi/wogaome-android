package com.yufan.library.base;

import android.app.Activity;

/**
 * Created by mengfantao on 18/7/16.
 */

public interface Pr {
    void onLoadMore(int index);
    void onRefresh();
    void onBackPressed();
    Activity getActivity();
}
