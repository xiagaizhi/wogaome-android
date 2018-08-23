package com.yushi.leke.fragment.openTreasureBox;

import android.os.Bundle;

import com.yufan.library.base.BaseFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yufan.library.inject.VuClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */
@VuClass(OpenTreasureBoxVu.class)
public class OpenTreasureBoxFragment extends BaseFragment<OpenTreasureBoxContract.IView> implements OpenTreasureBoxContract.Presenter {
    private List<String> datas = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datas.add("100");
        datas.add("200");
        datas.add("300");
        datas.add("400");
        datas.add("500");
        datas.add("600");
        vu.getmRecyclerView().getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public List<String> getDatas() {
        return datas;
    }
}
