package com.yufan.library.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.yufan.library.base.BaseApplication;
import com.yufan.library.manager.DialogManager;

/**
 * 作者：Created by zhanyangyang on 2018/9/1 11:24
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class StringUtil {
    public static void copyTextToBoard(String string) {
        if (TextUtils.isEmpty(string))
            return;
        ClipboardManager clip = (ClipboardManager) BaseApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
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

    public static String[] formatMoney(String money) {
        String head = "0";
        String tail = "";
        if (!TextUtils.isEmpty(money)) {
            String[] lkcs1 = money.split("\\.");
            String[] lkcs2 = money.split("//.");

            if (lkcs1.length > 1) {
                tail = lkcs1[1];
                if (TextUtils.isEmpty(tail)) {
                    head = lkcs1[0];
                } else {
                    head = lkcs1[0] + ".";
                }
            } else {
                if (lkcs2.length > 1) {
                    tail = lkcs2[1];
                    if (TextUtils.isEmpty(tail)) {
                        head = lkcs2[0];
                    } else {
                        head = lkcs2[0] + ".";
                    }
                } else {
                    head = money;
                    tail = "";
                }
            }
        }
        return new String[]{head, tail};
    }

    public static Spanned getMoneySpannble(String money, Context context) {
        String[] datas = formatMoney(money);
        return Html.fromHtml("<b><<font color='#151515'><size>" + datas[0] + "</size></font></b><font color='#333333'><size2>" + datas[1] + "</size2></font>", null, new SizeLabel(context));
    }
}
