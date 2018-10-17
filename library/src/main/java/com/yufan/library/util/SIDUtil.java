package com.yufan.library.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.yufan.library.Global;
import com.yufan.library.manager.SPManager;

/**
 * Created by mengfantao on 18/9/13.
 */

public class SIDUtil {

    public static final void setSID(Context context, String sid) {
        if (haveWriteSettingPermission(context)) {
            Settings.System.putString(context.getContentResolver(), "leke_sid", sid);
        }
        SPManager.getInstance().saveValue("leke_sid", sid);
        if (havesdcardPermission(context)) {
            FileUtil.saveSidToSdcard("leke_sid", sid);
        }
    }

    public static final String getSID(Context context) {
        String settingSID = "";
        String spSID;
        String sdcardSid = "";
        if (haveWriteSettingPermission(context)) {
            settingSID = Settings.System.getString(context.getContentResolver(), "leke_sid");
        }
        if (!TextUtils.isEmpty(settingSID)) {
            return settingSID;
        }
        spSID = SPManager.getInstance().getString("leke_sid", "");
        if (!TextUtils.isEmpty(spSID)) {
            return spSID;
        }
        if (havesdcardPermission(context)) {
            sdcardSid = FileUtil.getSid("leke_sid");
        }
        if (!TextUtils.isEmpty(sdcardSid)) {
            return sdcardSid;
        }
        return "";
    }


    /**
     * @param context
     * @return
     */
    public static boolean haveWriteSettingPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean havesdcardPermission(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            int permission1 = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            int permission2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }
    }
}
