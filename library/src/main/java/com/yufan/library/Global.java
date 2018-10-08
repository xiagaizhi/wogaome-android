package com.yufan.library;

import android.os.Environment;

import java.io.File;

/**
 * Created by mengfantao on 18/7/26.
 */

public class Global {
    /**
     * 浏览器页面url
     */
    public static final String BUNDLE_KEY_BROWSER_URL = "BUNDLE_KEY_BROWSER_URL";
    public static final String BUNDLE_KEY_NEED_REFRESH_MSG = "BUNDLE_KEY_NEED_REFRESH_MSG";

    public static final String BUNDLE_KEY_EXHIBITION_TYE = "BUNDLE_KEY_EXHIBITION_TYE";
    public static final String BUNDLE_AD_KEY = "BUNDLE_AD_KEY";
    public static final String BUNDLE_CHANNEL_ID = "BUNDLE_CHANNEL_ID";
    public static final String BUNDLE_CHANNEL_NAME = "BUNDLE_CHANNEL_NAME";
    public static final String BUNDLE_PROJECT_ID = "BUNDLE_PROJECT_ID";
    public static final String BUNDLE_UPDATE_MSG = "BUNDLE_UPDATE_MSG";
    public static final String BUNDLE_UPDATE_BASEINFO = "BUNDLE_UPDATE_BASEINFO";
    public static final String BUNDLE_UPDATE_PROFILE = "BUNDLE_UPDATE_PROFILE";

    public static final String SP_KEY_SERVICE_TYPE = "SP_KEY_SERVICE_TYPE";
    public static final String SP_KEY_MEDIA_ID = "SP_KEY_MEDIA_ID";
    public static final String SP_KEY_ALBUM_ID = "SP_KEY_ALBUM_ID";
    public static final String SP_KEY_MUSIC_PLAYER_GUIDE = "SP_KEY_MUSIC_GUIDE";
    public static final String MEDIA_METADATA_COMPAT = "MEDIA_METADATA_COMPAT";
    /**
     * 专辑ID和activityID
     */
    public static String BUNDLE_KEY_ACTIVITYID = "BUNDLE_KEY_ACTIVITYID";
    public static String BUNDLE_KEY_ALBUMID = "albumId";
    /**
     * 新手指引
     */
    public static final String SP_KEY_NEW_GUIDE = "SP_KEY_NEW_GUIDE";
    /**
     * 广告key
     */
    public static final String SP_AD_KEY = "SP_AD_KEY";
    public static final String SP_GUIDE_KEY = "SP_GUIDE_KEY";
    public static final String SP_AUDIO_TIMER_KEY = "SP_AUDIO_TIMER_KEY";
    public static final String SP_CURRENT_AUDIO_ID = "SP_CURRENT_AUDIO_ID";//当前播放AUDIO_ID
    public static final String SP_CURRENT_AUDIO_SCHEDULE = "SP_CURRENT_AUDIO_SCHEDULE";//当前播放AUDIO_ID 对应播放时长
    /**
     * 支付结果通知广播action
     */
    public static final String BROADCAST_PAY_RESUIL_ACTION = "BROADCAST_PAY_RESUIL_ACTION";

    /**
     * token失效通知广播action
     */
    public static final String BROADCAST_TOKEN_LOSE = "BROADCAST_TOKEN_LOSE";

    /**
     * 广告跳转
     */
    public static final String BROADCAST_ACTION_ADJUMP = "BROADCAST_ACTION_ADJUMP";

    /**
     * 更新
     */
    public static final String BROADCAST_ACTION_UPGRADE = "BROADCAST_ACTION_UPGRADE";

    /**
     * 信鸽消息
     */
    public static final String BROADCAST_ACTION_XGMESSAGE = "BROADCAST_ACTION_XGMESSAGE";

    public static final String BROADCAST_ACTION_REFRESH_MESSAGE= "BROADCAST_ACTION_REFRESH_MESSAGE";
    /**
     * 订阅数量消息
     */
    public static final String BROADCAST_ACTION_SUBCRIBE = "BROADCAST_ACTION_SUBCRIBE";
    /**
     * 支付结果返回data true:成功 false:失败
     */
    public static final String INTENT_PAY_RESUIL_DATA = "INTENT_PAY_RESUIL_DATA";

    public static final String SEARCH_KEY = "SEARCH_KEY";

    /**
     * 默认存放图片的路径
     */
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "leke"
            + File.separator + "image" + File.separator;
    public final static String DEFAULT_SAVE_CONFIG_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "leke"
            + File.separator + "config" + File.separator;

    /**
     * 裁剪之后图片路径
     */
    public final static String SAVE_TAILORING_IMAGE_PATH = DEFAULT_SAVE_IMAGE_PATH + "tailoringPhotos" + File.separator;

    /**
     * 压缩之后图片路径
     */
    public final static String SAVE_COMPRESSION_IMAGE_PATH = DEFAULT_SAVE_IMAGE_PATH + "compressionPhotos" + File.separator;

    /**
     * 屏幕截图
     */
    public final static String SAVE_SCREEN_CAPTURE_IMAGE_PATH = DEFAULT_SAVE_IMAGE_PATH + "screenCapture" + File.separator;

    /**
     * 下载图片
     */
    public final static String SAVE_DOWNLOAD_IMAGE_IMAGE_PATH = DEFAULT_SAVE_IMAGE_PATH + "downloadImage" + File.separator;


    /**
     * 分享生成海报
     */
    public final static String SAVE_SHARE_IMAGE_PATH = DEFAULT_SAVE_IMAGE_PATH + "share" + File.separator;

    /**
     * 保存sid
     */
    public final static String SAVE_SID_PATH = DEFAULT_SAVE_CONFIG_PATH + ".sid" + File.separator;

    /**
     * 崩溃日志收集
     */
    public final static String SAVE_LOG_PATH = Environment.getExternalStorageDirectory()
            + File.separator + "leke" + File.separator + "log" + File.separator;

}
