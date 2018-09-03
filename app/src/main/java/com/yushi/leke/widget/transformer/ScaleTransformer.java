package com.yushi.leke.widget.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ScaleTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.97f;
    private static final float MAX_SCALE = 0.9f;
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
        //    page.setAlpha(MAX_SCALE);
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MAX_SCALE);
        } else if (position <= 1) { // [-1,1]
        //  float scaleFactor = Math.max(MAX_SCALE, 1 - Math.abs(position));
            if (position < 0) {

                float scaleX = 1 + (1-MIN_SCALE) * position;
                page.setScaleX(scaleX);
                float scaleY = 1 + (1-MAX_SCALE) * position;
                page.setScaleY(scaleY);
            } else {
                float scaleX = 1 - (1-MIN_SCALE)* position;
                page.setScaleX(scaleX);
                float scaleY = 1 - (1-MAX_SCALE)* position;
                page.setScaleY(scaleY);
            }
        //   page.setAlpha(MAX_SCALE + (scaleFactor - MAX_SCALE) / (1 - MAX_SCALE) * (1 - MAX_SCALE));
        }
    }
}
