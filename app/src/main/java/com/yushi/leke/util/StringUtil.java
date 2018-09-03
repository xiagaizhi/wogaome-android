package com.yushi.leke.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import com.yufan.library.manager.DialogManager;
import com.yushi.leke.App;

/**
 * 作者：Created by zhanyangyang on 2018/9/1 11:24
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class StringUtil {
    public static void copyTextToBoard(String string) {
        if (TextUtils.isEmpty(string))
            return;
        ClipboardManager clip = (ClipboardManager) App.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(string);
        DialogManager.getInstance().toast("复制成功");
    }

    public static String handlePhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return "";
        } else {
            if (phoneNumber.length() >= 8) {
                return phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length());
            } else {
                return phoneNumber;
            }
        }
    }
}
