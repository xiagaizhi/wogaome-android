package com.yushi.leke.util;

import com.yufan.library.Global;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.manager.SPManager;
import com.yushi.leke.YFApi;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：Created by zhanyangyang on 2018/9/29 14:24
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class AudioTimerUtil {
    private static AudioTimerUtil instance;
    private long duration;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private long currentAccumulateTime;//当前上报时间点

    private AudioTimerUtil() {
        duration = SPManager.getInstance().getLong(Global.SP_AUDIO_TIMER_KEY, 0);
    }

    public static AudioTimerUtil getInstance() {
        if (instance == null) {
            instance = new AudioTimerUtil();
        }
        return instance;
    }

    public void startTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                duration++;
                SPManager.getInstance().saveValue(Global.SP_AUDIO_TIMER_KEY, duration);
                if (duration >= 600) {
                    accumulate();
                }
            }
        };
        mTimer.schedule(mTimerTask, 1000, 1000);
    }

    public void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 收听时长上报
     */
    public void accumulate() {
        if (duration <= 0) {
            return;
        }
        if (System.currentTimeMillis() - currentAccumulateTime < 10 * 60 * 1000) {//不足十分钟即便上报失败也不再上报，等再过十分钟再重试上报
            return;
        }
        currentAccumulateTime = System.currentTimeMillis();
        final long temp = duration;
        ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).accumulate(temp)).enqueue(new BaseHttpCallBack() {
            @Override
            public void onSuccess(ApiBean mApiBean) {
                duration = duration - temp;
                SPManager.getInstance().saveValue(Global.SP_AUDIO_TIMER_KEY, duration);
            }

            @Override
            public void onError(int id, Exception e) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
