package com.yushi.leke.fragment.exhibition.detail;

import android.content.Context;

import com.aliyun.vodplayerview.widget.AliyunScreenMode;
import com.aliyun.vodplayerview.widget.AliyunVodPlayerView;
import com.yufan.library.base.Pr;
import com.yufan.library.base.Vu;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface ExhibitionDetailContract {
    interface IView extends Vu {
        AliyunVodPlayerView getAliyunVodPlayerView();
        void hideShowMoreDialog(boolean from, AliyunScreenMode currentMode);
        void showMore(Context context);
    }

    interface Presenter extends Pr {
        void share();

    }
}
