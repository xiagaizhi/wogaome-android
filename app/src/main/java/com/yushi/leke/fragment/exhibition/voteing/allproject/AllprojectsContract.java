package com.yushi.leke.fragment.exhibition.voteing.allproject;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface AllprojectsContract {
    interface IView extends VuList {
        void showCityPickerView();
    }

    interface Presenter extends Pr {
        void Getmore();
        void GetmoreFromwork(String industry);
        void Getmorefrocity();
    }
}
