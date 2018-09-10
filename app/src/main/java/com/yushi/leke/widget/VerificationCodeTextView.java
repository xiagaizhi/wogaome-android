package com.yushi.leke.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.alibaba.verificationsdk.ui.IActivityCallback;
import com.alibaba.verificationsdk.ui.VerifyActivity;
import com.alibaba.verificationsdk.ui.VerifyType;
import com.yufan.library.R;
import com.yufan.library.api.ApiBean;
import com.yufan.library.api.ApiManager;
import com.yufan.library.api.BaseHttpCallBack;
import com.yufan.library.manager.DialogManager;
import com.yufan.library.util.CheckUtil;
import com.yushi.leke.YFApi;

import java.util.Map;


/**
 * Created by mengfantao on 18/8/15.
 */

@SuppressLint("AppCompatCustomView")
public class VerificationCodeTextView extends TextView {
    private  boolean needMobileExist;
    private OnGetCodeClickListener onGetCodeClickListener;

    public void setOnGetCodeClickListener(OnGetCodeClickListener onGetCodeClickListener) {
        this.onGetCodeClickListener = onGetCodeClickListener;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (msg.arg1 > 0) {
                    setText("重新获取(" + msg.arg1 + ")");
                    setTextColor(getResources().getColor(R.color.color_gray_levelc));
                    Message message = Message.obtain();
                    message.arg1 = msg.arg1 - 1;
                    message.what = 0;
                    sendMessageDelayed(message, 1000);
                } else {
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
        super(context,null);
    }

    public VerificationCodeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public VerificationCodeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.Verification, 0, 0);
        if (arr != null) {
            needMobileExist = arr.getBoolean(R.styleable.Verification_needMobileExist, true);
            arr.recycle();
        }
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
                if (onGetCodeClickListener == null) {
                    throw new RuntimeException("需要调用 setOnGetCodeClickListener");
                }
            String phone=    onGetCodeClickListener.getPhone();
                if(!CheckUtil.checkPhone(phone)){
                    DialogManager.getInstance().toast("手机号不能为空");
                    return;
                }
                ApiManager.getCall(ApiManager.getInstance().create(YFApi.class).mobileExist(phone)).enqueue(new BaseHttpCallBack() {
                    @Override
                    public void onSuccess(ApiBean mApiBean) {
                        if(needMobileExist=="true".equals(mApiBean.data)){
                            verifyUI();
                        }
                    }

                    @Override
                    public void onError(int id, Exception e) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
            }
        });
    }

    //滑动验证
    public void verifyUI(){
        VerifyActivity.startSimpleVerifyUI(getContext(), VerifyType.NOCAPTCHA, "0335", null, new IActivityCallback() {
            @Override
            public void onNotifyBackPressed() {

            }
            @Override
            public void onResult(int i, Map<String, String> map) {
                if (i == 1) {
                    onGetCodeClickListener.getCode(map.get("sessionID"));
                    setOnClickListener(null);
                    Message msg = Message.obtain();
                    msg.what = 0;
                    msg.arg1 = 60;
                    handler.sendMessage(msg);
                }
            }
        });
    }

    public interface OnGetCodeClickListener {
        void getCode(String sessionID);
        String  getPhone();
    }
}
