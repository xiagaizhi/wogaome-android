package com.yushi.leke.fragment.album.audioList;

import android.support.v4.media.MediaBrowserCompat;

import com.yufan.library.base.Pr;
import com.yufan.library.base.VuList;

import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */

public interface MediaBrowserContract {
    interface IView extends VuList {
      void   notifyDataSetChanged();
      void addMedia(List<MediaBrowserCompat.MediaItem> children);
      void updateTitle(String mediaId);
    }

    interface Presenter extends Pr {


    }
}
