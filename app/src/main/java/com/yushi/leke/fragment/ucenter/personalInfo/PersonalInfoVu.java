package com.yushi.leke.fragment.ucenter.personalInfo;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inject.AnnotateUtils;
import com.yufan.library.util.FastBlur;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by zhanyangyang on 18/8/25.
 */
@FindLayout(layout = R.layout.fragment_layout_personalinfo)
@Title("个人资料")
public class PersonalInfoVu extends BaseVu<PersonalInfoContract.Presenter> implements PersonalInfoContract.IView {
    @FindView(R.id.img_personal_top_bg)
    ImageView img_personal_top_bg;
    @FindView(R.id.img_head)
    SimpleDraweeView img_head;
    @FindView(R.id.ll_container)
    LinearLayout ll_container;
    @FindView(R.id.rl_edit_head)
    RelativeLayout rl_edit_head;


    private Bitmap originalBitmap;
    private Bitmap changeBitmap;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (changeBitmap != null && !changeBitmap.isRecycled()) {
                img_personal_top_bg.setImageBitmap(changeBitmap);
            }
        }
    };

    @Override
    public void initView(View view) {
        rl_edit_head.setOnClickListener(this);

        img_head.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg");
        Glide.with(getContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg")
                .asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                originalBitmap = resource;
                changeBitmap = FastBlur.doBlur(originalBitmap, 20, true);
                mHandler.sendEmptyMessage(1);
            }
        });

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        String titleNameStr = AnnotateUtils.getTitle(this);
        if (!TextUtils.isEmpty(titleNameStr)) {
            TextView titleName = appToolbar.creatCenterView(TextView.class);
            titleName.setText(titleNameStr);
            titleName.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        ImageView leftView = appToolbar.creatLeftView(ImageView.class);
        leftView.setImageResource(R.drawable.left_back_white_arrows);
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.onBackPressed();
            }
        });

        ImageView playerIcon = appToolbar.creatRightView(ImageView.class);
        playerIcon.setImageResource(R.drawable.ic_toolbar_player_white);
        playerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.openPlayer();
            }
        });

        appToolbar.build(false);
        return true;
    }

    int h = 0;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_edit_head:
                h = h + 100;
                ll_container.scrollTo(0, h);
                break;
        }
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public void destoryBitmap() {
        if (originalBitmap != null && !originalBitmap.isRecycled()) {
            originalBitmap.recycle();
        }
        if (changeBitmap != null && !changeBitmap.isRecycled()) {
            changeBitmap.recycle();
        }
    }
}
