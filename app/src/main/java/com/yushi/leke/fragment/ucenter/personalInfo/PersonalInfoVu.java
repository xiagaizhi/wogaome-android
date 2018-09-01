package com.yushi.leke.fragment.ucenter.personalInfo;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.AnnotateUtils;
import com.yufan.library.util.AreaUtil;
import com.yufan.library.view.ResizeLayout;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yushi.leke.R;
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
public class PersonalInfoVu extends BaseListVu<PersonalInfoContract.Presenter> implements PersonalInfoContract.IView {
    @FindView(R.id.img_personal_top_bg)
    ImageView img_personal_top_bg;
    @FindView(R.id.img_head)
    SimpleDraweeView img_head;
    @FindView(R.id.rv_personal)
    private YFRecyclerView rv_personal;
    @FindView(R.id.rsz_layout)
    private ResizeLayout rsz_layout;
    @FindView(R.id.rl_edit_head)
    RelativeLayout rl_edit_head;

    @Override
    public void initView(View view) {
        rl_edit_head.setOnClickListener(this);
        Glide.with(getContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg")
                .bitmapTransform(new BlurTransformation(getContext(), 15)).into(img_personal_top_bg);
        img_head.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg");
        rsz_layout.setOnKeyboardShowListener(new ResizeLayout.OnKeyboardChangedListener() {

            private boolean isShowed;

            @Override
            public void onKeyboardShow(int keyHeight) {
                isShowed = true;
                mPersenter.resetVu();
            }

            @Override
            public void onKeyboardHide() {
                if (isShowed) {
                    isShowed = false;
                    mPersenter.resetTab();
                }

            }

            @Override
            public void onKeyboardShowOver() {

            }
        });


    }


    private void showPickerView() {// 弹出选择器
        AreaUtil.getInstance().init(getContext());

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(AreaUtil.getInstance().getOptions1Items(), AreaUtil.getInstance().getOptions2Items(), AreaUtil.getInstance().getOptions3Items());//三级选择器
        pvOptions.show();
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


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_edit_head:
                showPickerView();
                break;


        }
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return rv_personal;
    }
}
