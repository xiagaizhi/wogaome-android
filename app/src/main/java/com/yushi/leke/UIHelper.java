package com.yushi.leke;

import com.yufan.library.bean.FragmentInfo;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by mengfantao on 18/7/27.
 */

public class UIHelper {

    public static FragmentInfo  creat(Class<? extends SupportFragment> clz){
        FragmentInfo fragment=  new FragmentInfo(clz);
        return fragment;
    }

}
