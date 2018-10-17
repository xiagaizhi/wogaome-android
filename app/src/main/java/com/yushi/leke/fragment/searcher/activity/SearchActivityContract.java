package com.yushi.leke.fragment.searcher.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface SearchActivityContract {
    interface IView extends VuList {
        EditText getEditText();
    }

    interface Presenter extends Pr {
        void search(String searchKey);
        boolean onKey(View v, int keyCode, KeyEvent event);
    }
}
