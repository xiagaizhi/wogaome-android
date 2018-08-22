package com.yushi.leke.fragment.main;

import android.view.View;

import com.yufan.library.widget.NestRadioGroup;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_main)
@Title("主页")
public class MainVu extends BaseVu<MainContract.Presenter> implements MainContract.IView {
    @FindView(R.id.rg_main_bar)
    NestRadioGroup rg_main_bar;
    private MenuModel[] menuModels = new MenuModel[3];
    private int currentPositon;

    @Override
    public void initView(View view) {
        for (int i = 0; i < menuModels.length; i++) {
            if (i == 0) {
                menuModels[i] = new MenuModel(rg_main_bar, MainMenu.SUBSCRIBE_COLUM);
            } else if (i == 1) {
                menuModels[i] = new MenuModel(rg_main_bar, MainMenu.ROAD_SHOW_HALL);
            } else if (i == 2) {
                menuModels[i] = new MenuModel(rg_main_bar, MainMenu.MY);
            }
        }
        rg_main_bar.setOnCheckedChangeListener(new NestRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                for (int i = 0; i < menuModels.length; i++) {
                    MenuModel model = menuModels[i];
                    if (checkedId == model.id) {
                        if (i != currentPositon) {
                            mPersenter.switchTab(currentPositon, i);
                            currentPositon = i;
                        }
                    }
                }
            }
        });
        rg_main_bar.check(menuModels[0].id);
        currentPositon = 0;
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
