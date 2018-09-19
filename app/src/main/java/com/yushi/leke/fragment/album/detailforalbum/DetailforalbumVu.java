package com.yushi.leke.fragment.album.detailforalbum;

import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

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
@FindLayout(layout = R.layout.fragment_layout_detailforalbum)
public class DetailforalbumVu extends BaseVu<DetailforalbumContract.Presenter> implements DetailforalbumContract.IView {
    @FindView(R.id.tv_album_detail)
    private TextView tv_album_detail;
    @Override
    public void initView(View view) {

    }

    @Override
    public boolean initTitle(AppToolbar appToolbar) {
        return false;
    }

    @Override
    public void initStatusLayout(StateLayout stateLayout) {
        super.initStatusLayout(stateLayout);
    }

    @Override
    public void showtext(Spanned introduction) {
        tv_album_detail.setText(introduction);
    }
}
