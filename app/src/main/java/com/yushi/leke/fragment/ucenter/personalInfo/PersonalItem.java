package com.yushi.leke.fragment.ucenter.personalInfo;

/**
 * Created by mengfantao on 18/8/31.
 */

public class PersonalItem {


    public String tabName;
    public String tabValue;
    public String tabEditHint;
    public boolean tabExt;
    public boolean inputTab;

    public PersonalItem(String tabName, String tabValue, String tabEditHint, boolean tabExt, boolean inputTab) {
        this.tabName = tabName;
        this.tabValue = tabValue;
        this.tabEditHint = tabEditHint;
        this.tabExt = tabExt;
        this.inputTab = inputTab;
    }

    @Override
    public boolean equals(Object obj) {
        return tabName.equals(((PersonalItem)obj).tabName);
    }
}
