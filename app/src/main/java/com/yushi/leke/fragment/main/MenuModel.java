package com.yushi.leke.fragment.main;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yushi.leke.R;
import com.yushi.leke.fragment.main.MainMenu;

/**
 * Created by mengfantao on 17/12/25.
 */

public class MenuModel {
    public MainMenu menu;
    public View mRootView;
    public TextView mBadge;
    public int id;//rootview的id
    public RadioButton rbView;

    public MenuModel(ViewGroup viewGroup, MainMenu menu) {
        this.menu = menu;
        mRootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_menu, viewGroup);
        rbView = (RadioButton) mRootView.findViewById(R.id.rb_0);
        id = View.generateViewId();
        rbView.setId(id);
        rbView.setText(menu.getTitle());
        Drawable top = viewGroup.getResources().getDrawable(menu.getIcon());
        rbView.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
        mRootView.setId(View.generateViewId());
        mBadge = (TextView) mRootView.findViewById(R.id.tv_badge);
    }
}
