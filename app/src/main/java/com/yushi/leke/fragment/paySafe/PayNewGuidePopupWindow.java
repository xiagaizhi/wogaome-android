package com.yushi.leke.fragment.paySafe;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.yufan.library.base.BasePopupWindow;

/**
 * Created by zhanyangyang on 18-8-24.
 */
public class PayNewGuidePopupWindow extends BasePopupWindow {
    private String content;
    private Button btnCopy;

    public PayNewGuidePopupWindow(Context context, String msg) {
        super(context);
        this.content = msg;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}
