package com.yushi.leke.fragment.exhibition.fourpage;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;
import com.yushi.leke.uamp.playback.Playback;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface fourpageContract {
    interface IView extends VuList {

    }
    interface Presenter extends Pr {
        void MyCallback();
    }
}
