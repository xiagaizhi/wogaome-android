package com.yushi.leke.fragment.ucenter.personalInfo;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inject.AnnotateUtils;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

import jp.wasabeef.glide.transformations.BlurTransformation;

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
    @FindView(R.id.rl_edit_head)
    RelativeLayout rl_edit_head;

    @FindView(R.id.rl_container_1)
    RelativeLayout rl_container_1;
    @FindView(R.id.view_line_1)
    View view_line_1;


    @FindView(R.id.rl_container_2)
    RelativeLayout rl_container_2;
    @FindView(R.id.view_line_2)
    View view_line_2;


    @FindView(R.id.rl_container_3)
    RelativeLayout rl_container_3;
    @FindView(R.id.view_line_3)
    View view_line_3;


    @FindView(R.id.rl_container_4)
    RelativeLayout rl_container_4;
    @FindView(R.id.view_line_4)
    View view_line_4;


    @FindView(R.id.rl_container_5)
    RelativeLayout rl_container_5;
    @FindView(R.id.view_line_5)
    View view_line_5;


    @FindView(R.id.rl_container_6)
    RelativeLayout rl_container_6;
    @FindView(R.id.view_line_6)
    View view_line_6;

    @FindView(R.id.rl_container_7)
    RelativeLayout rl_container_7;
    @FindView(R.id.view_line_7)
    View view_line_7;

    @FindView(R.id.rl_container_8)
    RelativeLayout rl_container_8;
    @FindView(R.id.view_line_8)
    View view_line_8;

    @FindView(R.id.rl_container_9)
    RelativeLayout rl_container_9;

    @Override
    public void initView(View view) {
        rl_edit_head.setOnClickListener(this);
        Glide.with(getContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg")
                .bitmapTransform(new BlurTransformation(getContext(), 15)).into(img_personal_top_bg);
        img_head.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg");
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


    private int i = 0;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_edit_head:
                if (i % 2 == 0) {
                    rl_container_1.setVisibility(View.GONE);
                    view_line_1.setVisibility(View.GONE);
                    rl_container_2.setVisibility(View.GONE);
                    view_line_2.setVisibility(View.GONE);
                }else {
                    rl_container_1.setVisibility(View.VISIBLE);
                    view_line_1.setVisibility(View.VISIBLE);
                    rl_container_2.setVisibility(View.VISIBLE);
                    view_line_2.setVisibility(View.VISIBLE);
                }
                i++;
                break;
        }
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
}
