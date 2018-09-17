package com.yushi.leke.fragment.exhibition.voteend.allproject;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface AllendContract {
    interface IView extends VuList {
        String getindustry();
        String getcity();
    }

    interface Presenter extends Pr {
        String getactivityid();
    }
}
