package com.yushi.leke.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.alibaba.verificationsdk.ui.IActivityCallback;
import com.alibaba.verificationsdk.ui.VerifyActivity;
import com.alibaba.verificationsdk.ui.VerifyType;
import com.yufan.library.R;
import com.yufan.library.manager.DialogManager;

import java.util.Map;


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
                if(onGetCodeClickListener==null){
                    throw new RuntimeException("需要调用 setOnGetCodeClickListener");
                }

                VerifyActivity.startSimpleVerifyUI(getContext(), VerifyType.NOCAPTCHA, "0335", null, new IActivityCallback() {
                    @Override
                    public void onNotifyBackPressed() {

                    }

                    @Override
                    public void onResult(int i, Map<String, String> map) {
                        if(i==1){
                            if(onGetCodeClickListener.getCode(map.get("sessionID"))){
                                setOnClickListener(null);
                                Message msg = Message.obtain();
                                msg.what = 0;
                                msg.arg1 = 60;
                                handler.sendMessage(msg);
                            }else {
                                DialogManager.getInstance().toast("请输入手机号");
                            }
                        }
                    }
                });

            }
        });
    }

    public interface OnGetCodeClickListener {
        boolean getCode(String sessionID);
    }
}
