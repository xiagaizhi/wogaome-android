package com.yushi.leke.fragment.ucenter;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.view.ptr.PtrClassicFrameLayout;
import com.yufan.library.view.ptr.PtrDefaultHandler;
import com.yufan.library.view.ptr.PtrFrameLayout;
import com.yufan.library.view.ptr.PtrHandler;
import com.yushi.leke.R;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_ucenter)
@Title("我的")
public class UCenterVu extends BaseVu<UCenterContract.Presenter> implements UCenterContract.IView {
    @FindView(R.id.img_messgae)
    ImageView img_messgae;
    @FindView(R.id.view_red_point)
    View view_red_point;
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
    @FindView(R.id.rl_roadshow)
    RelativeLayout rl_roadshow;
    @FindView(R.id.tv_roadshow_num)
    TextView tv_roadshow_num;
    @FindView(R.id.rl_subscribe)
    RelativeLayout rl_subscribe;
    @FindView(R.id.tv_subscribe_num)
    TextView tv_subscribe_num;
    @FindView(R.id.rl_vote)
    RelativeLayout rl_vote;
    @FindView(R.id.rl_wallet)
    RelativeLayout rl_wallet;
    @FindView(R.id.tv_mylkc)
    TextView tv_mylkc;
    @FindView(R.id.rl_level)
    RelativeLayout rl_level;
    @FindView(R.id.tv_mylevel)
    TextView tv_mylevel;
    @FindView(R.id.rl_myvip)
    RelativeLayout rl_myvip;
    @FindView(R.id.rl_share)
    RelativeLayout rl_share;
    @FindView(R.id.tv_share_num)
    TextView tv_share_num;
    @FindView(R.id.rl_setting)
    RelativeLayout rl_setting;
    @FindView(R.id.ptr)
    PtrClassicFrameLayout mPtrClassicFrameLayout;
    @FindView(R.id.tv_vote_num)
    TextView tv_vote_num;

    @Override
    public void initView(View view) {
        ll_personal_info.setOnClickListener(this);
        img_messgae.setOnClickListener(this);
        img_player.setOnClickListener(this);
        rl_roadshow.setOnClickListener(this);
        rl_subscribe.setOnClickListener(this);
        rl_vote.setOnClickListener(this);
        rl_wallet.setOnClickListener(this);
        rl_level.setOnClickListener(this);
        rl_myvip.setOnClickListener(this);
        rl_share.setOnClickListener(this);
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
            case R.id.img_messgae://消息
                break;
            case R.id.img_player://播放器
                mPersenter.startPlayer();
                break;
            case R.id.ll_personal_info://个人资料
                mPersenter.openPersonalpage();
                break;
            case R.id.rl_roadshow://我的路演
                mPersenter.openBrowserPage("我的路演");
                break;
            case R.id.rl_subscribe://我的订阅
                mPersenter.openBrowserPage("我的订阅");
                break;
            case R.id.rl_vote://我的投票
                mPersenter.openBrowserPage("我的投票");
                break;
            case R.id.rl_wallet://我的钱包
                mPersenter.openMyWallet();
                break;
            case R.id.rl_level://我的等级
                mPersenter.openBrowserPage("我的等级");
                break;
            case R.id.rl_myvip://我的会员
                mPersenter.openBrowserPage("我的会员");
                break;
            case R.id.rl_share://分享好友
                mPersenter.openBrowserPage("分享好友");
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
    public void hasUnreadMsg(boolean hasUnreadMsg) {
        if (hasUnreadMsg) {
            view_red_point.setVisibility(View.VISIBLE);
        } else {
            view_red_point.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateMyInfo(MyProfileInfo myProfileInfo, MyBaseInfo myBaseInfo) {
        if (myProfileInfo != null) {
            tv_roadshow_num.setText("" + myProfileInfo.getRoadShow());
            tv_subscribe_num.setText("" + myProfileInfo.getSubscription());
            tv_vote_num.setText("" + myProfileInfo.getVote());
            tv_mylkc.setText("" + myProfileInfo.getToken());
            tv_mylevel.setText("" + myProfileInfo.getLevel());
            tv_share_num.setText("已邀请：" + myProfileInfo.getInvitation() + "名好友");
        }
        if (myBaseInfo != null) {
            img_head.setImageURI(myBaseInfo.getAvatar());
            tv_name.setText(myBaseInfo.getUserName());
            tv_personal_introduce.setText(myBaseInfo.getMotto());
        }
    }
}
