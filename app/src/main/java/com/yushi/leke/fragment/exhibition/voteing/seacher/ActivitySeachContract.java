package com.yushi.leke.fragment.exhibition.voteing.seacher;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface ActivitySeachContract {
    interface IView extends VuList {
        EditText getEditText();
    }

    interface Presenter extends Pr {
        void search(String searchKey);
        boolean onKey(View v, int keyCode, KeyEvent event);
    }
}
