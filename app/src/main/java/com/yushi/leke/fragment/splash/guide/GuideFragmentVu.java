package com.yushi.leke.fragment.splash.guide;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_guidefragment)
@Title("指引")
public class GuideFragmentVu extends BaseVu<GuideFragmentContract.Presenter> implements GuideFragmentContract.IView {
    @FindView(R.id.guide_viewpager)
    ViewPager guide_viewpager;
    @FindView(R.id.guide_dots)
    LinearLayout guide_dots;

    private int[] images = new int[]{R.drawable.guide_img_1, R.drawable.guide_img_2, R.drawable.guide_img_3};
    private List<View> viewList = new ArrayList<>();
    private GuidePagerAdapter mAdapter;

    @Override
    public void initView(View view) {
        for (int i = 0; i < images.length; i++) {
            viewList.add(initPagerItemView(i, images[i]));
            guide_dots.addView(initDots(i));
        }
        mAdapter = new GuidePagerAdapter(viewList);
        guide_viewpager.setAdapter(mAdapter);


        guide_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < guide_dots.getChildCount(); i++) {
                    ((ImageView) guide_dots.getChildAt(i)).setImageResource(R.drawable.ic_dot_unchecked);
                }
                ((ImageView) guide_dots.getChildAt(arg0)).setImageResource(R.drawable.ic_dot_checked);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {


            }
        });
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }


    private View initPagerItemView(int position, int resId) {
        View view = LayoutInflater.from(getContext()).inflate(
                R.layout.item_guide_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iguide_img);
        ImageView btn_go_login = (ImageView) view.findViewById(R.id.btn_go_login);
        btn_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.jumpToMain();
            }
        });
        if (position == images.length - 1) {
            btn_go_login.setVisibility(View.VISIBLE);
        } else {
            btn_go_login.setVisibility(View.GONE);
        }
        imageView.setImageResource(resId);
        return view;
    }

    private ImageView initDots(int i) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) getContext().getResources().getDimension(R.dimen.px8), 0, (int) getContext().getResources().getDimension(R.dimen.px8), 0);
        imageView.setLayoutParams(layoutParams);
        if (i == 0) {
            imageView.setImageResource(R.drawable.ic_dot_checked);
        } else {
            imageView.setImageResource(R.drawable.ic_dot_unchecked);
        }
        return imageView;
    }
}
