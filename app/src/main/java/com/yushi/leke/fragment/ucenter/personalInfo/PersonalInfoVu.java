package com.yushi.leke.fragment.ucenter.personalInfo;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.inject.AnnotateUtils;
import com.yufan.library.widget.citypicker.Interface.OnCityItemClickListener;
import com.yufan.library.widget.citypicker.bean.CityBean;
import com.yufan.library.widget.citypicker.bean.DistrictBean;
import com.yufan.library.widget.citypicker.bean.ProvinceBean;
import com.yufan.library.widget.citypicker.citywheel.CityConfig;
import com.yufan.library.widget.citypicker.style.citypickerview.CityPickerView;
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

    @FindView(R.id.ll_container_1)
    LinearLayout ll_container_1;
    @FindView(R.id.ll_container_2)
    LinearLayout ll_container_2;
    @FindView(R.id.ll_container_3)
    LinearLayout ll_container_3;
    @FindView(R.id.ll_container_4)
    LinearLayout ll_container_4;
    @FindView(R.id.ll_container_5)
    LinearLayout ll_container_5;
    @FindView(R.id.ll_container_6)
    LinearLayout ll_container_6;
    @FindView(R.id.ll_container_7)
    LinearLayout ll_container_7;
    @FindView(R.id.ll_container_8)
    LinearLayout ll_container_8;

    @FindView(R.id.et_userName)
    EditText et_userName;
    @FindView(R.id.et_gender)
    TextView et_gender;
    @FindView(R.id.et_company)
    EditText et_company;
    @FindView(R.id.et_position)
    EditText et_position;
    @FindView(R.id.et_introduce)
    EditText et_introduce;
    @FindView(R.id.et_email)
    EditText et_email;
    @FindView(R.id.tv_city)
    TextView tv_city;
    @FindView(R.id.et_detail_address)
    EditText et_detail_address;
    @FindView(R.id.rl_rootview)
    RelativeLayout rl_rootview;
    private CityPickerView mCityPickerView = new CityPickerView();

    @Override
    public void initView(View view) {
        mCityPickerView.init(getContext());
        CityConfig cityConfig = new CityConfig.Builder().title("选择城市").build();
        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    sb.append(province.getName() + " " + province.getId() + "\n");
                }

                if (city != null) {
                    sb.append(city.getName() + " " + city.getId() + ("\n"));
                }

                if (district != null) {
                    sb.append(district.getName() + " " + district.getId() + ("\n"));
                }

                tv_city.setText("" + sb.toString());
            }

            @Override
            public void onCancel() {
                super.onCancel();
                resetView(1);
            }
        });

        rl_edit_head.setOnClickListener(this);
        et_userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetView(2);
                }
            }
        });
        et_gender.setOnClickListener(this);
        et_company.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetView(4);
                }
            }
        });
        et_position.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetView(5);
                }
            }
        });
        et_introduce.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetView(6);
                }
            }
        });
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetView(7);
                }
            }
        });
        tv_city.setOnClickListener(this);

        et_detail_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetView(9);
                }
            }
        });

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


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_edit_head:
                break;
            case R.id.et_gender:
                resetView(3);
                break;
            case R.id.tv_city:
                resetView(8);
                mCityPickerView.showCityPicker();
                break;

        }
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    private void resetView(int position) {
        if (position == 1) {
            ll_container_1.setVisibility(View.VISIBLE);
            ll_container_2.setVisibility(View.VISIBLE);
            ll_container_3.setVisibility(View.VISIBLE);
            ll_container_4.setVisibility(View.VISIBLE);
            ll_container_5.setVisibility(View.VISIBLE);
            ll_container_6.setVisibility(View.VISIBLE);
            ll_container_7.setVisibility(View.VISIBLE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 2) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.VISIBLE);
            ll_container_3.setVisibility(View.VISIBLE);
            ll_container_4.setVisibility(View.VISIBLE);
            ll_container_5.setVisibility(View.VISIBLE);
            ll_container_6.setVisibility(View.VISIBLE);
            ll_container_7.setVisibility(View.VISIBLE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 3) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.GONE);
            ll_container_3.setVisibility(View.VISIBLE);
            ll_container_4.setVisibility(View.VISIBLE);
            ll_container_5.setVisibility(View.VISIBLE);
            ll_container_6.setVisibility(View.VISIBLE);
            ll_container_7.setVisibility(View.VISIBLE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 4) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.GONE);
            ll_container_3.setVisibility(View.GONE);
            ll_container_4.setVisibility(View.VISIBLE);
            ll_container_5.setVisibility(View.VISIBLE);
            ll_container_6.setVisibility(View.VISIBLE);
            ll_container_7.setVisibility(View.VISIBLE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 5) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.GONE);
            ll_container_3.setVisibility(View.GONE);
            ll_container_4.setVisibility(View.GONE);
            ll_container_5.setVisibility(View.VISIBLE);
            ll_container_6.setVisibility(View.VISIBLE);
            ll_container_7.setVisibility(View.VISIBLE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 6) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.GONE);
            ll_container_3.setVisibility(View.GONE);
            ll_container_4.setVisibility(View.GONE);
            ll_container_5.setVisibility(View.GONE);
            ll_container_6.setVisibility(View.VISIBLE);
            ll_container_7.setVisibility(View.VISIBLE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 7) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.GONE);
            ll_container_3.setVisibility(View.GONE);
            ll_container_4.setVisibility(View.GONE);
            ll_container_5.setVisibility(View.GONE);
            ll_container_6.setVisibility(View.GONE);
            ll_container_7.setVisibility(View.VISIBLE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 8) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.GONE);
            ll_container_3.setVisibility(View.GONE);
            ll_container_4.setVisibility(View.GONE);
            ll_container_5.setVisibility(View.GONE);
            ll_container_6.setVisibility(View.GONE);
            ll_container_7.setVisibility(View.GONE);
            ll_container_8.setVisibility(View.VISIBLE);
        } else if (position == 9) {
            ll_container_1.setVisibility(View.GONE);
            ll_container_2.setVisibility(View.GONE);
            ll_container_3.setVisibility(View.GONE);
            ll_container_4.setVisibility(View.GONE);
            ll_container_5.setVisibility(View.GONE);
            ll_container_6.setVisibility(View.GONE);
            ll_container_7.setVisibility(View.GONE);
            ll_container_8.setVisibility(View.GONE);
        }
    }
}
