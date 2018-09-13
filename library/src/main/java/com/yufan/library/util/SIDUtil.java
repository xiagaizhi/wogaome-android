package com.yufan.library.util;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.yufan.library.manager.SPManager;

/**
 * Created by mengfantao on 18/9/13.
 */

public class SIDUtil {

    public static final void setSID(Context context,String sid){
        Settings.System.putString(context.getContentResolver(), "leke_sid", sid);
        SPManager.getInstance().saveValue("leke_sid",sid);
    }
    public static final String getSID(Context context){
      String settingSID=  Settings.System.getString(context.getContentResolver(), "leke_sid");
      String spSID=  SPManager.getInstance().getString("leke_sid","");
        if(!TextUtils.isEmpty(settingSID)){
            return settingSID;
        }
        if(!TextUtils.isEmpty(spSID)){
            return spSID;
        }
        return "";

    }
}
