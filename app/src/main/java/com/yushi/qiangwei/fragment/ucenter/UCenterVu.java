package com.yushi.qiangwei.fragment.ucenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.view.ptr.PtrClassicFrameLayout;
import com.yufan.library.view.ptr.PtrDefaultHandler;
import com.yufan.library.view.ptr.PtrFrameLayout;
import com.yufan.library.view.ptr.PtrHandler;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.widget.StateLayout;
import com.yushi.qiangwei.R;
import com.yushi.qiangwei.UIHelper;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_ucenter)
@Title("我的")
public class UCenterVu extends BaseVu<UCenterContract.Presenter> implements UCenterContract.IView {
    @FindView(R.id.img_player)
    ImageView img_player;
    @FindView(R.id.ll_personal_info)
    View ll_personal_info;
    @FindView(R.id.img_head)
    SimpleDraweeView img_head;
    @FindView(R.id.img_mylevel)
    ImageView img_mylevel;
    @FindView(R.id.tv_name)
    TextView tv_name;
    @FindView(R.id.img_vip)
    ImageView img_vip;
    @FindView(R.id.tv_personal_introduce)
    TextView tv_personal_introduce;
    @FindView(R.id.rl_sub)
    RelativeLayout rl_subscribe;
    @FindView(R.id.rl_level)
    RelativeLayout rl_level;
    @FindView(R.id.tv_mylevel)
    TextView tv_mylevel;
    @FindView(R.id.rl_setting)
    RelativeLayout rl_setting;
    @FindView(R.id.ptr)
    PtrClassicFrameLayout mPtrClassicFrameLayout;

    @Override
    public void initView(View view) {
        ll_personal_info.setOnClickListener(this);
        img_player.setOnClickListener(this);
       UIHelper. putImageView(mPersenter.getActivity(),img_player,UCenterVu.class);
        rl_subscribe.setOnClickListener(this);
        rl_level.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        mPtrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPersenter.toRefresh();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_player://播放器
                mPersenter.startPlayer();
                break;
            case R.id.ll_personal_info://个人资料
                mPersenter.openPersonalpage();
                break;
            case R.id.rl_sub://我的订阅
                mPersenter.openBrowserPage("我的订阅");
                break;
            case R.id.rl_level://我的等级
                mPersenter.openBrowserPage("我的等级");
                break;
            case R.id.rl_setting://设置
                mPersenter.openSettingPage();
                break;
        }
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        appToolbar.creatCenterView(TextView.class);
        appToolbar.build();
        return true;
    }


    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }


    @Override
    public void updateMyInfo(MyProfileInfo myProfileInfo, MyBaseInfo myBaseInfo) {
        if (myProfileInfo != null) {
            if (TextUtils.equals("1", myProfileInfo.getLevel())) {
                img_mylevel.setVisibility(View.VISIBLE);
                img_mylevel.setImageResource(R.drawable.ic_level_bronze);
                tv_mylevel.setText("青铜");
            } else if (TextUtils.equals("2", myProfileInfo.getLevel())) {
                img_mylevel.setVisibility(View.VISIBLE);
                img_mylevel.setImageResource(R.drawable.ic_level_silver);
                tv_mylevel.setText("白银");
            } else if (TextUtils.equals("3", myProfileInfo.getLevel())) {
                img_mylevel.setVisibility(View.VISIBLE);
                img_mylevel.setImageResource(R.drawable.ic_level_gold);
                tv_mylevel.setText("黄金");
            } else if (TextUtils.equals("4", myProfileInfo.getLevel())) {
                img_mylevel.setVisibility(View.VISIBLE);
                img_mylevel.setImageResource(R.drawable.ic_level_platinum);
                tv_mylevel.setText("铂金");
            } else if (TextUtils.equals("5", myProfileInfo.getLevel())) {
                img_mylevel.setVisibility(View.VISIBLE);
                img_mylevel.setImageResource(R.drawable.ic_level_diamond);
                tv_mylevel.setText("钻石");
            } else if (TextUtils.equals("6", myProfileInfo.getLevel())) {
                img_mylevel.setVisibility(View.VISIBLE);
                img_mylevel.setImageResource(R.drawable.ic_level_supreme);
                tv_mylevel.setText("至尊");
            } else {
                img_mylevel.setVisibility(View.GONE);
            }
            if (myProfileInfo.isVip()) {
                img_vip.setVisibility(View.VISIBLE);
            } else {
                img_vip.setVisibility(View.GONE);
            }
        }
        if (myBaseInfo != null) {
            img_head.setImageURI(myBaseInfo.getAvatar());
            tv_name.setText(myBaseInfo.getUserName());
            if (TextUtils.isEmpty(myBaseInfo.getMotto())) {
                tv_personal_introduce.setText("这家伙太懒了，什么都没留下。");
            } else {
                tv_personal_introduce.setText(myBaseInfo.getMotto());
            }

        }
    }

    @Override
    public void refreshComplete() {
        mPtrClassicFrameLayout.refreshComplete();
    }
}
