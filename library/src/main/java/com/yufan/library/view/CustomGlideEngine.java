//package com.yufan.library.view;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.net.Uri;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//import com.zhihu.matisse.engine.ImageEngine;
//
///**
// * Created by yechao on 2018/2/8.
// * Describe :
// */
//
//public class CustomGlideEngine implements ImageEngine {
//
//    @Override
//    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
////        RequestOptions options = new RequestOptions()
////                .centerCrop()
////                .placeholder(placeholder)//这里可自己添加占位图
////                .error(R.mipmap.ic_launcher)//这里可自己添加出错图
////                .override(resize, resize);
//        Glide.with(context) // some .jpeg files are actually gif
//                .load(uri)
//                .asBitmap()
//                .into(imageView);
//    }
//
//    @Override
//    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
////        RequestOptions options = new RequestOptions()
////                .centerCrop()
////                .placeholder(placeholder)//这里可自己添加占位图
////                .error(R.mipmap.ic_launcher)//这里可自己添加出错图
////                .override(resize, resize);
//        Glide.with(context)
//                .load(uri)
//                .asBitmap()
//
////                .apply(options)
//                .into(imageView);
//    }
//
//    @Override
//    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
////        RequestOptions options = new RequestOptions()
////                .centerCrop()
////                .override(resizeX, resizeY)
////                .priority(Priority.HIGH);
//        Glide.with(context)
//                .load(uri)
////                .apply(options)
//                .into(imageView);
//    }
//
//    @Override
//    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
////        RequestOptions options = new RequestOptions()
////                .centerCrop()
////                .override(resizeX, resizeY)
////                .priority(Priority.HIGH);
//        Glide.with(context)
////                .asGif()
//                .load(uri)
////                .apply(options)
//                .into(imageView);
//    }
//
//    @Override
//    public boolean supportAnimatedGif() {
//        return true;
//    }
//}
