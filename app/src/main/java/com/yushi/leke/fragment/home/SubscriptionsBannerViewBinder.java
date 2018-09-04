/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yushi.leke.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inter.ICallBack;
import com.yufan.library.util.FormatImageUrlUtils;
import com.yufan.library.view.banner.Banner;
import com.yufan.library.view.banner.BannerConfig;
import com.yufan.library.view.banner.listener.OnBannerClickListener;
import com.yufan.library.view.banner.loader.ImageLoaderInterface;
import com.yushi.leke.R;
import com.yushi.leke.activity.MusicPlayerActivity;
import com.yushi.leke.widget.transformer.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;


/**
 * 首页订阅专栏，头部
 */
public class SubscriptionsBannerViewBinder extends ItemViewBinder<SubscriptionBanner, SubscriptionsBannerViewBinder.ViewHolder> {
    private ICallBack callBack;
    public static final int BANNER_BINDER_MUSIC = 1;
    public static final int BANNER_BINDER_SEARCH = 2;

    public SubscriptionsBannerViewBinder(ICallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {

        return new ViewHolder(inflater.inflate(R.layout.item_top_subscriptions, parent, false));

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final SubscriptionBanner category) {

        holder.rightMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnBackResult(BANNER_BINDER_MUSIC);

            }
        });

        holder.rl_searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnBackResult(BANNER_BINDER_SEARCH);
            }
        });
        holder.rightMusic.setBackgroundResource(R.drawable.ic_blue_music);
        holder.mConvenientBanner.setPageTransformer(false, new ScaleTransformer());
        holder.mConvenientBanner.setOffscreenPageLimit(3);
        List<String> list = new ArrayList<>();
        list.add("http://oss.cyzone.cn/2018/0810/20180810095648712.jpg");
        list.add("http://oss.cyzone.cn/2018/0808/20180808022315645.png");
        list.add("http://oss.cyzone.cn/2018/0731/20180731065933886.jpeg");
        list.add("http://oss.cyzone.cn/2018/0730/20180730071658744.jpg");
        list.add("http://oss.cyzone.cn/2015/1228/20151228041950459.jpg");
        list.add("http://oss.cyzone.cn/2018/0116/20180116052244372.png");
        holder.mConvenientBanner.setImages(list).setImageLoader(new ImageLoaderInterface<View>() {
            @Override
            public void displayImage(int position, Context context, Object path, View view) {
                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view;
                simpleDraweeView.setImageURI((String)path);
            }

            @Override
            public View createImageView(Context context) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
                GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(context.getResources())
                        //设置圆形圆角参数
                        //设置圆角半径
                        .setRoundingParams(RoundingParams.fromCornersRadius(20))
                        //分别设置左上角、右上角、左下角、右下角的圆角半径
                        //.setRoundingParams(RoundingParams.fromCornersRadii(20,25,30,35))
                        //分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径
                        //.setRoundingParams(RoundingParams.fromCornersRadii(new float[]{20,25,30,35,40,45,50,55}))
                        //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                        //.setRoundingParams(RoundingParams.asCircle())
                        //设置淡入淡出动画持续时间(单位：毫秒ms)
                        .setFadeDuration(300)
                        //构建
                        .build();
                //设置Hierarchy

                simpleDraweeView.setHierarchy(hierarchy);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                simpleDraweeView.setLayoutParams(layoutParams);
                return simpleDraweeView;
            }
        });
        holder.mConvenientBanner.setOnBannerClickListener(new OnBannerClickListener() {


            @Override
            public void OnBannerClick(int position) {

            }
        });
        holder.mConvenientBanner.setIndicatorGravity(BannerConfig.CENTER);
        holder.mConvenientBanner.setDelayTime(5000);
        holder.mConvenientBanner.start();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView rightMusic;
        private RelativeLayout rl_searchbar;
        private Banner mConvenientBanner;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            rightMusic = itemView.findViewById(R.id.iv_anim_icon);
            rl_searchbar = itemView.findViewById(R.id.rl_searchbar);
            mConvenientBanner=itemView.findViewById(R.id.viewpager);
            mConvenientBanner.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View view) {
                  mConvenientBanner.startAutoPlay();

                }

                @Override
                public void onViewDetachedFromWindow(View view) {
                   mConvenientBanner.stopAutoPlay();
                }
            });
        }
    }



}
