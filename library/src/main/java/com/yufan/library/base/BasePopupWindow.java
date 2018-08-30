package com.yufan.library.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.yufan.library.R;

/**
 * Created by zhan on 16-3-16.
 */
public class BasePopupWindow extends PopupWindow implements View.OnClickListener {
    private Context context;
    private FrameLayout mRootView;

    public void addView(View view) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRootView.addView(view, layoutParams);
    }

    public BasePopupWindow(Context context) {
        super(context);
        this.context = context;
        initView(initPopupWindowView(context));
    }

    public Context getContext() {
        return context;
    }

    protected View initPopupWindowView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mPopupWindowView = inflater.inflate(getLayoutId(), null);
        this.setContentView(mPopupWindowView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.mystyle);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.color_73000000));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimBottomPopupWindow);

        return mPopupWindowView;
    }

    public void initView(View view) {
        mRootView = view.findViewById(R.id.id_rootview);
    }


    protected int getLayoutId() {
        return R.layout.layout_base_popupwindow;
    }

    @Override
    public void onClick(View view) {

    }
}
