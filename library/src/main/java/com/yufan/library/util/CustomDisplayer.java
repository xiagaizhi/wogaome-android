package com.yufan.library.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lwkandroid.imagepicker.utils.IImagePickerDisplayer;

/**
 * 作者：Created by zhanyangyang on 2018/9/5 17:27
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class CustomDisplayer implements IImagePickerDisplayer {
    @Override
    public void display(Context context, String url, ImageView imageView, int maxWidth, int maxHeight) {
        Glide.with(context).load(url).into(imageView);
    }

    @Override
    public void display(Context context, String url, ImageView imageView, int placeHolder, int errorHolder, int maxWidth, int maxHeight) {
        Glide.with(context).load(url).into(imageView);
    }
}
