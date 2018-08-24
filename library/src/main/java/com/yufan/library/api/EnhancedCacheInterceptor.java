package com.yufan.library.api;

import android.util.Log;

import com.yufan.library.base.BaseApplication;
import com.yufan.library.cache.CacheManager;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class EnhancedCacheInterceptor implements Interceptor {
    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        String url = request.url().toString();
        RequestBody requestBody = request.body();
        Charset charset = Charset.forName("UTF-8");
        StringBuilder sb = new StringBuilder();
        sb.append(url);
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
        Log.d("CacheManager", "EnhancedCacheInterceptor -> key:" + sb.toString());

        ResponseBody responseBody =  response.body();
        MediaType contentType = responseBody.contentType();

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        String key = sb.toString();
        //服务器返回的json原始数据 
        String json = buffer.clone().readString(charset);

        CacheManager.saveObject(BaseApplication.getInstance(),key, json);
        Log.d("CacheManager", "put cache-> key:" + key + "-> json:" + json);
        return chain.proceed(request);
    }
}
