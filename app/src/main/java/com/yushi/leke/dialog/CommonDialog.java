package com.yushi.leke.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.yushi.leke.R;

/**
 * 作者：Created by zhanyangyang on 2018/8/29 14:53
 * 邮箱：zhanyangyang@hzyushi.cn
 */

public class CommonDialog extends Dialog {
    public static final int COMMONDIALOG_ACTION_POSITIVE = 1;
    public static final int COMMONDIALOG_ACTION_NEGATIVE = 2;
    private TextView tv_title;
    private TextView tv_negative;
    private TextView tv_positive;
    private View view_line;

    public CommonDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected CommonDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = d.getWidth();
        window.setAttributes(p);
        window.setDimAmount(0.6f);
    }

    private void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.dialog_common_layout, null);
        setContentView(rootView);
        tv_title = rootView.findViewById(R.id.tv_title);
        tv_negative = rootView.findViewById(R.id.tv_negative);
        tv_positive = rootView.findViewById(R.id.tv_positive);
        view_line = rootView.findViewById(R.id.view_line);
    }

    public CommonDialog setTitle(String title) {
        tv_title.setText(title);
        return this;
    }

    public CommonDialog setNegativeName(String negativeName) {
        tv_negative.setText(negativeName);
        return this;
    }

    public CommonDialog setPositiveName(String positiveName) {
        tv_positive.setText(positiveName);
        return this;
    }

    public CommonDialog setCommonClickListener(final CommonDialogClick commonClickListener) {
        tv_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonClickListener != null) {
                    commonClickListener.onClick(CommonDialog.this, COMMONDIALOG_ACTION_POSITIVE);
                }
            }
        });

        tv_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commonClickListener != null) {
                    commonClickListener.onClick(CommonDialog.this, COMMONDIALOG_ACTION_NEGATIVE);
                }
            }
        });
        return this;
    }

    public CommonDialog setHaveNegative(boolean isHaveNegative) {
        if (isHaveNegative) {
            tv_negative.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
        } else {
            tv_negative.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        }
        return this;
    }

    public CommonDialog setCommonDialogCanceledOnTouchOutside2(boolean cancel) {
        this.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public interface CommonDialogClick {
        void onClick(CommonDialog commonDialog, int actionType);
    }
}
