package com.yushi.leke.fragment.album.detailforalbum;

import android.text.Spanned;

import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface DetailforalbumContract {
    interface IView extends Vu {
        void showtext(Spanned s);
    }

    interface Presenter extends Pr {


    }
}
