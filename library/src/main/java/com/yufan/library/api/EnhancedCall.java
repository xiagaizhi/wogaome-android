package com.yufan.library.api;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yufan.library.base.BaseApplication;
import com.yufan.library.cache.CacheManager;
import com.yufan.library.util.MD5Util;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnhancedCall{
    private Call<okhttp3.ResponseBody> mCall;
    // 是否使用缓存 默认开启
    private boolean mUseCache = false;
    private String mCache;

    public EnhancedCall(Call<okhttp3.ResponseBody> call) {
        this.mCall = call;
        Request request = call.request();
        String url = request.url().toString();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?");
        if (request.method().equals("POST")) {
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(Charset.forName("UTF-8"));
            }
            Buffer buffer = new Buffer();
            try {
                requestBody.writeTo(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            sb.append(buffer.readString(charset));
            buffer.close();
        }
        mCache = (String) CacheManager.readObject(BaseApplication.getInstance(), MD5Util.MD5(sb.toString()));

    }



    /**
     * 是否使用缓存 默认使用
     */
    public EnhancedCall useCache(boolean useCache) {
        mUseCache = useCache;
        return this;
    }

    public void enqueue(final BaseHttpCallBack handler) {
    if(mUseCache&&!TextUtils.isEmpty(mCache)){
        ApiBean mApiBean = JSON.parseObject(mCache, ApiBean.class);
        mApiBean.json=mCache;
        handler.onResponse(mApiBean);
    }

        mCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    int code = response.code();
                    if (code == 200) {
                        String mjson = response.body().string();
                        ApiBean mApiBean = JSON.parseObject(mjson, ApiBean.class);
                        mApiBean.json=mjson;
                        if (TextUtils.equals(ApiBean.TOKEN_LOSE,mApiBean.code)) {
                            //token失效
                            return;
                        }

                        handler.onResponse(mApiBean);
                    } else {
                        handler.onFailure(code, new Exception(response.errorBody().string()));
                    }
                } catch (IOException e) {
                    handler.onFailure(-1, new Exception("接口格式异常"));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!mUseCache ) {
                    //不使用缓存 或者网络可用 的情况下直接回调onFailure
                    Exception exception = new Exception(t.getMessage());
                    handler.onFailure(-1, exception);
                    return;
                }
                if (TextUtils.isEmpty(mCache) ) {
                    Exception exception = new Exception(t.getMessage());
                    handler.onFailure(-1, exception);
                }
            }
        });
    }
}
