package com.yufan.library.widget.anim;

import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;

/**
 * Created by mengfantao on 18/9/3.
 */

public class AFVerticalAnimator extends DefaultVerticalAnimator {
    public AFVerticalAnimator() {
        enter = me.yokeyword.fragmentation.R.anim.v_fragment_enter;
        exit = me.yokeyword.fragmentation.R.anim.v_fragment_exit;
        popEnter = me.yokeyword.fragmentation.R.anim.pop_exit_no_anim;
        popExit = me.yokeyword.fragmentation.R.anim.pop_exit_no_anim;
    }
}
