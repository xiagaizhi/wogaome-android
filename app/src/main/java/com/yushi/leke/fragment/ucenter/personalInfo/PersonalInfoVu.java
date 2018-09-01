package com.yushi.leke.fragment.ucenter.personalInfo;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
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

import java.util.List;

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
    @FindView(R.id.rl_submit_container)
    RelativeLayout rl_submit_container;
    @FindView(R.id.tv_ok)
    TextView tv_ok;
    @FindView(R.id.tv_cancel)
    TextView tv_cancel;
    EditText mCurrentInputBox;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                rl_submit_container.setVisibility(View.VISIBLE);
            }
        }
    };

    @Override
    public void initView(View view) {
        rl_edit_head.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        Glide.with(getContext()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg")
                .bitmapTransform(new BlurTransformation(getContext(), 15)).into(img_personal_top_bg);
        img_head.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535611272761&di=edb2ad0ac1e9fae8c791398bffecffdd&imgtype=0&src=http%3A%2F%2Fp1.wmpic.me%2Farticle%2F2017%2F10%2F23%2F1508744874_AaXhrBZE.jpg");
        rsz_layout.setOnKeyboardShowListener(new ResizeLayout.OnKeyboardChangedListener() {
            private boolean isShowed;

            @Override
            public void onKeyboardShow(int keyHeight) {
                isShowed = true;
                mPersenter.resetVu();
                mHandler.sendEmptyMessage(100);
            }

            @Override
            public void onKeyboardHide() {
                if (isShowed) {
                    isShowed = false;
                    mPersenter.resetTab();
                }
                rl_submit_container.setVisibility(View.GONE);
            }

            @Override
            public void onKeyboardShowOver() {

            }
        });
    }

    @Override
    public void showCityPickerView() {
        if (AreaUtil.getInstance().getOptions1Items() == null ||
                AreaUtil.getInstance().getOptions2Items() == null ||
                AreaUtil.getInstance().getOptions3Items() == null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    AreaUtil.getInstance().init(getContext());
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    toShowCityPickView();
                }
            }.execute();

        } else {
            toShowCityPickView();
        }

    }

    @Override
    public void showGenderPickerView(final List<String> genderList) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mPersenter.resetTab();
                mPersenter.selectGender(genderList.get(options1));
            }
        })
                .setTitleText("性别选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        pvOptions.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                mPersenter.resetTab();
            }
        });

        pvOptions.setPicker(genderList);
        pvOptions.show();
        mPersenter.resetVu();
    }

    @Override
    public void setCurrentInputBox(EditText editText) {
        mCurrentInputBox = editText;
    }

    private void toShowCityPickView() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                mPersenter.resetTab();
                mPersenter.selectedCityInfo(options1, options2, options3);
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        pvOptions.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {
                mPersenter.resetTab();
            }
        });
        pvOptions.setPicker(AreaUtil.getInstance().getOptions1Items(), AreaUtil.getInstance().getOptions2Items(), AreaUtil.getInstance().getOptions3Items());//三级选择器
        pvOptions.show();
        mPersenter.resetVu();
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

                break;
            case R.id.tv_ok:
                if (mCurrentInputBox != null) {
                    mPersenter.toSubmitData(mCurrentInputBox.getText().toString());
                }
                mPersenter.hideSoftInput();
                break;
            case R.id.tv_cancel:
                mPersenter.hideSoftInput();
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
