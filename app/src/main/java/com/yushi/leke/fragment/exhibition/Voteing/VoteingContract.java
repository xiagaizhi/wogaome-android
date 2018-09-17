package com.yushi.leke.fragment.exhibition.Voteing;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface VoteingContract {
    interface IView extends VuList {

    }
    interface Presenter extends Pr {
        void MyCallback();
    }
}
