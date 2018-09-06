package com.yushi.leke.fragment.searcher.activity;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface SearchActivityContract {
    interface IView extends VuList {
        void setTextKey(String textKey);
    }

    interface Presenter extends Pr {
        void search(String searchKey);

    }
}
