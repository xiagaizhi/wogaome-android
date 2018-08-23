package com.yushi.leke.fragment.vodplayer;

import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.aliyun.vodplayerview.view.choice.AlivcShowMoreDialog;
import com.aliyun.vodplayerview.view.more.AliyunShowMoreValue;
import com.aliyun.vodplayerview.view.more.ShowMoreView;
import com.aliyun.vodplayerview.view.more.SpeedValue;
import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.yufan.library.inject.FindView;
import com.yufan.library.base.BaseVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.Title;
import com.yufan.library.widget.StateLayout;
import com.yufan.library.widget.AppToolbar;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layoutName = "alivc_player_layout_skin")
@Title("播放器")
public class VodPlayerVu extends BaseVu<VodPlayerContract.Presenter> implements VodPlayerContract.IView {
    private AliyunVodPlayerView mAliyunVodPlayerView;
    private AlivcShowMoreDialog showMoreDialog;

    @Override
    public void initView(View view) {
        mAliyunVodPlayerView=   view.findViewById(com.aliyun.vodplayer.R.id.video_view);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
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
                Toast.makeText(getContext(), "功能开发中, 敬请期待...", Toast.LENGTH_SHORT).show();
            }
        });

        showMoreView.setOnBarrageButtonClickListener(new ShowMoreView.OnBarrageButtonClickListener() {
            @Override
            public void onBarrageClick() {
                Toast.makeText(getContext(), "功能开发中, 敬请期待...", Toast.LENGTH_SHORT).show();
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
}
