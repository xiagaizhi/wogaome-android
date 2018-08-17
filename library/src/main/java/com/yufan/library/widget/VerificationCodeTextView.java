package com.yufan.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.yufan.library.R;


/**
 * Created by mengfantao on 18/8/15.
 */

@SuppressLint("AppCompatCustomView")
public class VerificationCodeTextView extends TextView {
    private OnGetCodeClickListener onGetCodeClickListener;

    public void setOnGetCodeClickListener(OnGetCodeClickListener onGetCodeClickListener) {
        this.onGetCodeClickListener = onGetCodeClickListener;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if(msg.arg1>0){
                    setText("重新获取(" + msg.arg1 + ")");
                    setTextColor(getResources().getColor(R.color.color_gray_levelc));
                    Message message = Message.obtain();
                    message.arg1 = msg.arg1 - 1;
                    message.what=0;
                    sendMessageDelayed(message, 1000);
                }else {
                    reset();
                }

            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeMessages(0);
        handler.removeCallbacksAndMessages(null);
    }

    public VerificationCodeTextView(Context context) {
        super(context);
    }

    public VerificationCodeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VerificationCodeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        reset();

    }

    private void reset() {
        setText("获取验证码");
        setTextColor(getResources().getColor(R.color.color_blue_level6));
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onGetCodeClickListener.getCode();
                setOnClickListener(null);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.arg1 = 60;
                handler.sendMessage(msg);
            }
        });
    }

    public interface OnGetCodeClickListener {
        void getCode();
    }
}
