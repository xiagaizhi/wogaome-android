package com.yushi.leke.fragment.exhibition.voteing.allproject;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface AllprojectsContract {
    interface IView extends VuList {
        long getindustry();
        String getcity();
        void setCitylist(List list);
        void setWorklist(List list,List list2);
        void seacherOnclick();
    }

    interface Presenter extends Pr {
        String getactivityid();
        void getCitylist();
        void getWorklist();
    }
}
