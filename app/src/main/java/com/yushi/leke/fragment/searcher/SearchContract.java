package com.yushi.leke.fragment.searcher;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface SearchContract {
    interface IView extends VuList {

        String getSearchKey();
        EditText getEditText();
    }

    interface Presenter extends Pr {

    void search(String searchKey);
        boolean onKey(View v, int keyCode, KeyEvent event);

    }
}
