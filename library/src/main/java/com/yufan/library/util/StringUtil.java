package com.yufan.library.util;

import android.content.ClipboardManager;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;

import com.yufan.library.base.BaseApplication;
import com.yufan.library.manager.DialogManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static String formatTime(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = format.parse(startDate);
            Date date2 = format.parse(endDate);
            if (date2.getYear() > date1.getYear()) {
                return startDate + "／" + endDate;
            } else {
                return startDate + "／" + endDate.substring(5, endDate.length());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return startDate + "／" + endDate;
        }

    }


    public static String stringForTime(int timeMs) {
        timeMs = timeMs/1000;
        StringBuilder sb = new StringBuilder();
        int seconds = timeMs % 60;
        int minutes = (timeMs / 60) % 60;
        int hours = timeMs / 3600;
        if (hours == 0) {
            sb.append("00:");
        } else if (hours < 10) {
            sb.append("0");
            sb.append(hours);
            sb.append(":");
        } else {
            sb.append(hours);
            sb.append(":");
        }
        if (minutes == 0) {
            sb.append("00:");
        } else if (minutes < 10) {
            sb.append("0");
            sb.append(minutes);
            sb.append(":");
        } else {
            sb.append(minutes);
            sb.append(":");
        }
        if (seconds == 0) {
            sb.append("00");
        } else if (seconds < 10) {
            sb.append("0");
            sb.append(seconds);
        } else {
            sb.append(seconds);
        }
        return sb.toString();
    }
    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime + 0));
    }
}
