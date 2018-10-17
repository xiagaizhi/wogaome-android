package com.yufan.library.widget.anim;

import com.yufan.library.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;

/**
 * Created by mengfantao on 18/9/3.
 */

public class AFHorizontalAnimator extends DefaultHorizontalAnimator {
    public AFHorizontalAnimator() {
        enter = R.anim.c_h_fragment_enter;
        exit = R.anim.c_h_fragment_exit;
        popEnter = me.yokeyword.fragmentation.R.anim.pop_exit_no_anim;
        popExit = me.yokeyword.fragmentation.R.anim.pop_exit_no_anim;
    }
}
