package com.yushi.leke.fragment.exhibition.voteend.allproject;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.widget.StateLayout;
import com.yushi.leke.R;
import com.yushi.leke.UIHelper;
import com.yushi.leke.YFApi;

import java.util.ArrayList;
import java.util.List;

@FindLayout(layout = R.layout.xx_allproject_main)
@Title("参赛项目活动")
public class AllendVu extends BaseListVu<AllendContract.Presenter> implements AllendContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    private TextView tv_choose_city, tv_choose_work;
    private ImageView img_city,img_work;
    long instroid=-1;
    ImageView backButton;
    private TextView mTitleView;
    private ImageView img_seacher;
    @Override
    public void initView(View view) {
        super.initView(view);
        initData();
    }
    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }
    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        img_seacher = appToolbar.creatRightView(ImageView.class);
        img_seacher.setImageResource(R.drawable.ic_search_blue);

        mTitleView=     appToolbar.creatCenterView(TextView.class);
        mTitleView.setText("参赛项目活动");
        mTitleView .getPaint().setFakeBoldText(true);
        backButton=   appToolbar.creatLeftView(ImageView.class);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onBackPressed();
            }
        });
        backButton.setImageResource(com.yufan.library.R.drawable.left_back_black_arrows);
        img_seacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.seacherOnclick();
            }
        });
        appToolbar.build();

        return true;
    }
    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tv_choose_city = (TextView) findViewById(R.id.tv_choose_city);
        tv_choose_city.setClickable(true);
        tv_choose_city.setOnClickListener(clickListener);
        tv_choose_work = (TextView) findViewById(R.id.tv_choose_work);
        tv_choose_work.setClickable(true);
        tv_choose_work.setOnClickListener(clickListener);
        img_city= (ImageView) findViewById(R.id.img_city);
        img_city.setOnClickListener(clickListener);
        img_work= (ImageView) findViewById(R.id.img_work);
        img_work.setOnClickListener(clickListener);
    }

    /**
     * 监听事件
     */
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_choose_city:
                    mPersenter.getCitylist();
                    break;
                case R.id.img_city:
                    mPersenter.getCitylist();
                    break;
                case R.id.tv_choose_work:
                    mPersenter.getWorklist();
                    break;
                case R.id.img_work:
                    mPersenter.getWorklist();
                    break;
            }
        }
    };
    @Override
    public long getindustry() {
        return instroid;
    }

    @Override
    public String getcity() {
        return  String.valueOf(tv_choose_city.getText());
    }

    @Override
    public void setCitylist(final List citylist) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_choose_city.setText( String.valueOf(citylist.get(options1)));
                mPersenter.onRefresh();
            }
        })
                .setTitleText("选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(citylist);//一级选择器
        pvOptions.show();
    }

    @Override
    public void setWorklist(final List worklist,final List worklistid) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_choose_work.setText( String.valueOf(worklist.get(options1)));
                instroid= (long) worklistid.get(options1);
                mPersenter.onRefresh();
            }
        }).setTitleText("选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(worklist);//一级选择器
        pvOptions.show();
    }
}
