package com.yushi.leke.fragment.splash.guide;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：Created by zhanyangyang on 2018/9/15 13:30
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class GuidePagerAdapter extends PagerAdapter {

    private List<View> data;

    public GuidePagerAdapter(List<View> data) {
        super();
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(data.get(position));
        return data.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(data.get(position));
    }

}
