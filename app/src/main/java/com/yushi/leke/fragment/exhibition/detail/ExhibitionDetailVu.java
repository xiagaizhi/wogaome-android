package com.yushi.leke.fragment.exhibition.detail;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
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
@FindLayout(layout = R.layout.fragment_layout_exhibitiondetail)
@Title("路演详情")
public class ExhibitionDetailVu extends BaseVu<ExhibitionDetailContract.Presenter> implements ExhibitionDetailContract.IView {
    @FindView(R.id.video_view)
    private AliyunVodPlayerView mAliyunVodPlayerView;
    private AlivcShowMoreDialog showMoreDialog;
    private ImageView shareIcon;
    private ImageView detailIcon;

    @Override
    public void initView(View view) {
        LinearLayout mToolBarRightContainer = mAliyunVodPlayerView.getmControlView().getToolBarRightContainer();
        mToolBarRightContainer.addView(detailIcon);
        mToolBarRightContainer.addView(shareIcon);
    }


    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }


    public void showMore(final Context activity) {
        showMoreDialog = new AlivcShowMoreDialog(activity);
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(mAliyunVodPlayerView.getCurrentSpeed());
        moreValue.setVolume(mAliyunVodPlayerView.getCurrentVolume());
        moreValue.setScreenBrightness(mAliyunVodPlayerView.getCurrentScreenBrigtness());
        ShowMoreView showMoreView = new ShowMoreView(activity, moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();


        showMoreView.setOnScreenCastButtonClickListener(new ShowMoreView.OnScreenCastButtonClickListener() {
            @Override
            public void onScreenCastClick() {

            }
        });

        showMoreView.setOnBarrageButtonClickListener(new ShowMoreView.OnBarrageButtonClickListener() {
            @Override
            public void onBarrageClick() {

            }
        });

        showMoreView.setOnSpeedCheckedChangedListener(new ShowMoreView.OnSpeedCheckedChangedListener() {
            @Override
            public void onSpeedChanged(RadioGroup group, int checkedId) {
                // 点击速度切换
                if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_normal) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
                } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_onequartern) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
                } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_onehalf) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
                } else if (checkedId == com.aliyun.vodplayer.R.id.rb_speed_twice) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
                }

            }
        });

        // 亮度seek
        showMoreView.setOnLightSeekChangeListener(new ShowMoreView.OnLightSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                mAliyunVodPlayerView.setCurrentScreenBrigtness(progress);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        showMoreView.setOnVoiceSeekChangeListener(new ShowMoreView.OnVoiceSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                mAliyunVodPlayerView.setCurrentVolume(progress);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

    }

    @Override
    public AliyunVodPlayerView getAliyunVodPlayerView() {
        return mAliyunVodPlayerView;
    }

    @Override
    public void hideShowMoreDialog(boolean from, AliyunScreenMode currentMode) {
        if (showMoreDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                showMoreDialog.dismiss();

            }
        }
    }


    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        shareIcon = appToolbar.creatRightView(ImageView.class);
        shareIcon.setImageResource(R.drawable.ic_share_white);
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAliyunVodPlayerView.changeScreenMode(AliyunScreenMode.Small);
                mPersenter.share();
            }
        });
        detailIcon = appToolbar.creatRightView(ImageView.class);
        detailIcon.setImageResource(R.drawable.ic_activity_instructions);
        detailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPersenter.openActivityInstruction();
            }
        });
        return false;
    }

}
