package com.aliyun.vodplayerview.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：Created by zhanyangyang on 2018/9/29 14:24
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class VideoTimerUtil {
    private static VideoTimerUtil instance;
    private long duration;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private VideoTimerUtil() {
    }

    public static VideoTimerUtil getInstance() {
        if (instance == null) {
            instance = new VideoTimerUtil();
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
