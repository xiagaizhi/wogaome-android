package com.yushi.leke.fragment.album;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.util.ViewUtils;
import com.yufan.library.view.ptr.PtrClassicFrameLayout;
import com.yufan.library.view.ptr.PtrFrameLayout;
import com.yufan.library.view.ptr.PtrHandler;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.widget.ExpandableTextView;
import com.yufan.library.widget.NoScrollViewPager;
import com.yufan.library.widget.StateLayout;
import com.yushi.leke.R;
import com.yushi.leke.UIHelper;
import com.yushi.leke.util.FormatImageUtil;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.fragment_layout_album)
@Title("专辑详情")
public class AlbumDetailVu extends BaseVu<AlbumDetailContract.Presenter> implements AlbumDetailContract.IView {
    @FindView(R.id.tab_bar)
    private TabLayout mTabLayout;
    @FindView(R.id.viewpager)
    private NoScrollViewPager mViewPager;
    @FindView(R.id.sdv)
    private SimpleDraweeView sdv;
    @FindView(R.id.tv_album_detail)
    private ExpandableTextView expandableTextView;
    @FindView(R.id.tv_sub_title)
    private TextView tv_sub_title;
    @FindView(R.id.tv_subject_title)
    private TextView tv_subject_title;
    @FindView(R.id.tv_name)
    private TextView tv_name;
    @FindView(R.id.tv_playcount)
    private TextView tv_playcount;
    @FindView(R.id.tv_total)
    private TextView tv_total;
    @FindView(R.id.sdv_sub)
    private SimpleDraweeView sdv_sub;
    @FindView(R.id.tv_sub)
    private TextView tv_sub;
    private ImageView musicAnim;
    @FindView(R.id.app_bar_layout)
    private AppBarLayout appBarLayout;
    ImageView backButton;

    @FindView(R.id.ptr)
    private PtrClassicFrameLayout ptrClassicFrameLayout;
    private Boolean flag=false;//全局刷新判定
    private ImageView img_share;
    private int verticalOffset;
    @Override
    public void initView(View view) {
        mTabLayout.addTab(mTabLayout.newTab().setText("课程内容"));
        mTabLayout.addTab(mTabLayout.newTab().setText("专辑详情"));
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                ViewUtils.setIndicator(mTabLayout,60,60);
            }
        });
        //applayout竖直方向偏移量监听
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state,int verticalOffset) {
                Log.d("STATE", state.name());
                AlbumDetailVu.this.verticalOffset=verticalOffset;
                if( state == State.EXPANDED ) {
                    //展开状态
                    flag=true;
                    expandableTextView.setVisibility(View.VISIBLE);
                    backButton.setImageResource(com.yufan.library.R.drawable.left_back_white_arrows);
                    img_share.setImageResource(R.drawable.ic_share_white);
                    musicAnim.setImageResource(R.drawable.anim_player_white);
                    ((AnimationDrawable) musicAnim.getDrawable()).start();

                }else if(state == State.COLLAPSED){
                    //折叠状态
                    expandableTextView.setVisibility(View.INVISIBLE);
                    backButton.setImageResource(com.yufan.library.R.drawable.left_back_black_arrows);
                    img_share.setImageResource(R.drawable.ic_share_blue);
                    musicAnim.setImageResource(R.drawable.anim_player_blue);
                    ((AnimationDrawable) musicAnim.getDrawable()).start();
                }else {

                    //中间状态

                }
            }
        });
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //处理刷新逻辑
              mPersenter.onRefresh();

            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return verticalOffset==0;
            }
        });

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        img_share=appToolbar.creatRightView(ImageView.class);
        img_share.setImageResource(R.drawable.ic_share_white);
        backButton=   appToolbar.creatLeftView(ImageView.class);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onBackPressed();
            }
        });
        backButton.setImageResource(com.yufan.library.R.drawable.left_back_white_arrows);
        appToolbar.build(false);
        musicAnim = UIHelper.getMusicView(mPersenter.getActivity(),appToolbar);
        musicAnim.setImageResource(R.drawable.anim_player_white);
        ((AnimationDrawable) musicAnim.getDrawable()).start();
        musicAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( v.getAlpha()>0) {
                    mPersenter.onMusicMenuClick();
                }
            }
        });
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.onShareclick();
            }
        });
        return true;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public void showtext(AlbumDetailinfo info) {
        if (info!=null){
            tv_subject_title.setText(info.getAlbum().getCreator()+"·"+info.getAlbum().getAlbumName());
            tv_name.setText(info.getAlbum().getCreator()+"·简介");
            tv_playcount.setText(info.getAlbumViewTimes()+"次");
            tv_total.setText("全"+info.getAudioQuantity()+"集");
            expandableTextView.setText(info.getAlbum().getCreatorInfo());
            sdv.setImageURI(FormatImageUtil.converImageUrl(info.getAlbum().getHorizontalIcon(),0,562));
        }
    }

    @Override
    public void showsubstate(int state) {
        switch (state){
            //未订阅
            case 0:
                sdv_sub.setImageURI("res:///" +R.drawable.ic_sub_unstate);
                tv_sub.setText("订阅");
                tv_sub.setTextColor(Color.parseColor("#FF666666"));
                sdv_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPersenter.register();
                    }
                });
                break;
            //已订阅
            case 1:
                sdv_sub.setImageURI("res:///" +R.drawable.ic_sub_onstate);
                tv_sub.setText("已订阅");
                sdv_sub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPersenter.unregister();
                    }
                });
                break;
        }
    }

    @Override
    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    public SimpleDraweeView getDraweeView() {
        return sdv;
    }

    @Override
    public PtrClassicFrameLayout getPTR() {
        return ptrClassicFrameLayout;
    }
}
