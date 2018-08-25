package com.yushi.leke.fragment.openTreasureBox;

import android.support.v7.widget.RecyclerView;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface OpenTreasureBoxContract {
    interface IView extends Vu {
        RecyclerView getmRecyclerView();
    }

    interface Presenter extends Pr {
        List<GoodsInfo> getDatas();
        void selectGoodInfo(GoodsInfo goodsInfo);
        void toPay();
        void openTreasureBoxDetail();
    }
}
