package com.yushi.leke.fragment.splash.advert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_adfragment)
@Title("广告")
public class AdFragmentVu extends BaseVu<AdFragmentContract.Presenter> implements AdFragmentContract.IView {
    @FindView(R.id.tv_skip)
    TextView tv_skip;
    @FindView(R.id.img_ad)
    ImageView img_ad;

    @Override
    public void initView(View view) {
        tv_skip.setOnClickListener(this);
        img_ad.setOnClickListener(this);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public void updateAd(AdInfo adInfo, Bitmap bitmap) {
        img_ad.setImageBitmap(bitmap);
    }

    @Override
    public void updateCountdown(int seconds) {
        tv_skip.setText(seconds + "s跳过");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_skip:
                mPersenter.jumpToMain();
                break;
            case R.id.img_ad:
                mPersenter.openAdDetail();
                break;
        }
    }
}
