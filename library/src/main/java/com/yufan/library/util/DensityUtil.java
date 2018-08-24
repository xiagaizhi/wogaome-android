package com.yufan.library.util;

import android.content.Context;
import android.util.DisplayMetrics;

/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 * 手机分辨率工具类
 */

public class DensityUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenW(Context aty) {
        new DisplayMetrics();
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }

    public static int getScreenH(Context aty) {
        new DisplayMetrics();
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
