package com.yufan.library.util;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import org.xml.sax.XMLReader;

/**
 * 作者：Created by zhanyangyang on 2018/8/28 20:29
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class SizeLabel implements Html.TagHandler {
    private int startIndex = 0;
    private int stopIndex = 0;
    private Context mContext;

    public SizeLabel(Context context) {
        this.mContext = context;
    }

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals("size")) {
            if (opening) {
                startIndex = output.length();
            } else {
                stopIndex = output.length();
                output.setSpan(new AbsoluteSizeSpan(PxUtil.convertDIP2PX(mContext, 50)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else if (tag.toLowerCase().equals("size2")) {
            if (opening) {
                startIndex = output.length();
            } else {
                stopIndex = output.length();
                output.setSpan(new AbsoluteSizeSpan(PxUtil.convertDIP2PX(mContext, 30)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

    }
}
