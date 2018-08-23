package com.yufan.library.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yufan.library.R;

/**
 * Created by mengfantao on 18/8/23.
 */

public class LoginLineView extends RelativeLayout {
    private View view;
    private boolean inited;
    public LoginLineView(Context context) {
        super(context);
    }

    public LoginLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
         view = new View(getContext());
        view.setBackgroundColor(Color.parseColor("#80007AFF"));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        addView(view, layoutParams);
        inited=false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!inited){
            inited=true;
            int width=   MeasureSpec.getSize(widthMeasureSpec);
            view.setTranslationX(-width);
        }
    }

    public void startAnim() {
        view.animate().translationX(0).setDuration(300).start();
    }
    public void hintAnim(){
        view.setTranslationX(-getWidth());
    }
}
