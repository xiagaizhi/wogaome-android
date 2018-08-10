package com.yushi.leke.plugin.operation.test;

import android.view.View;
import android.widget.TextView;

import com.yufan.library.base.BaseListVu;
import com.yufan.library.inject.FindLayout;
import com.yufan.library.inject.FindView;
import com.yufan.library.inject.Title;
import com.yufan.library.view.recycler.YFRecyclerView;
import com.yufan.library.widget.AppToolbar;
import com.yushi.leke.plugin.R;


/**
 * Created by mengfantao on 18/7/2.
 */

@Title("测试标题")
@FindLayout(layout = R.layout.layout_fragment_list)
public class TestVu extends BaseListVu<DbTestContract.Presenter> implements DbTestContract.View {
    @FindView(R.id.recyclerview)
    private YFRecyclerView mYFRecyclerView;
    /**
     * 初始化title
     * @param toolbar
     * @return
     */
    @Override
    public boolean initTitle(AppToolbar toolbar) {
        super.initTitle(toolbar);
      TextView tvInsert=  toolbar.creatRightView(TextView.class);
        tvInsert.setText("分享");
        tvInsert.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              mPersenter.share();

          }
      });

        TextView tvUpdate=  toolbar.creatRightView(TextView.class);
        tvUpdate.setText("播放器");
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPersenter.startPlayer();
            }
        });
        toolbar.build();
        return true;
    }



    @Override
    public YFRecyclerView getRecyclerView() {
        return mYFRecyclerView;
    }





}
