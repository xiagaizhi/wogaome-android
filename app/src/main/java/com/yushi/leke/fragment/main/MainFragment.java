package com.yushi.leke.fragment.main;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.yufan.library.inject.VuClass;
import com.yushi.leke.R;
import com.yushi.leke.UIHelper;
import com.yushi.leke.fragment.home.SubscriptionsFragment;
import com.yushi.leke.fragment.openTreasureBox.OpenTreasureBoxFragment;
import com.yushi.leke.fragment.roadShow.RoadshowHallFragment;
import com.yushi.leke.fragment.test.TestListFragment;
import com.yushi.leke.fragment.ucenter.UCenterFragment;
import com.yushi.leke.fragment.wallet.MyWalletFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(MainVu.class)
public class MainFragment extends BaseFragment<MainContract.IView> implements MainContract.Presenter {
    private SupportFragment[] mFragments = new SupportFragment[3];
    private long[] mHits = new long[2];

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportFragment firstFragment = findChildFragment(TestListFragment.class);
        if (firstFragment == null) {
            mFragments[0] = UIHelper.creat(SubscriptionsFragment.class).build();
            mFragments[1] = UIHelper.creat(RoadshowHallFragment.class).build();
            mFragments[2] = UIHelper.creat(UCenterFragment.class).build();
            loadMultipleRootFragment(R.id.realtabcontent, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(RoadshowHallFragment.class);
            mFragments[2] = findChildFragment(UCenterFragment.class);
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressedSupport() {
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - 1000)) {
            //退出
            return super.onBackPressedSupport();
        } else {
            //提示
            Toast.makeText(_mActivity, "再次点击退出", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    public void switchTab(int formPositon, int toPosition) {
        showHideFragment(mFragments[toPosition], mFragments[formPositon]);
    }
}
