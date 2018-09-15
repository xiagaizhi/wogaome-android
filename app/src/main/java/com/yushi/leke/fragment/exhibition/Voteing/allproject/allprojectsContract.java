package com.yushi.leke.fragment.exhibition.Voteing.allproject;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface allprojectsContract {
    interface IView extends VuList {
        void showCityPickerView();
    }

    interface Presenter extends Pr {


        String selectedCityInfo(int options1, int options2);
    }
}
