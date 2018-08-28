package com.yushi.leke.uamp.ui;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat;
import android.view.View;
import android.widget.TextView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yufan.library.widget.AppToolbar;
import com.yufan.library.widget.StateLayout;
import com.yushi.leke.R;
import com.yushi.leke.activity.MusicPlayerActivity;

import java.util.List;

/**
 * Created by mengfantao on 18/8/2.
 */
@FindLayout(layout = R.layout.layout_fragment_list)
@Title("音频播放列表")
public class MediaBrowserVu extends BaseListVu<MediaBrowserContract.Presenter> implements MediaBrowserContract.IView {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;


    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
      TextView textView=  appToolbar.creatRightView(TextView.class);
      textView.setText("播放器");
      textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent openUI = new Intent(getContext(), MusicPlayerActivity.class);
              openUI.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
              getContext().startActivity(openUI);
          }
      });
        return super.initTitle(appToolbar);
    }

    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    public void addMedia(List<MediaBrowserCompat.MediaItem> children) {

    }

    @Override
    public void updateTitle(String mediaId) {

    }
}
