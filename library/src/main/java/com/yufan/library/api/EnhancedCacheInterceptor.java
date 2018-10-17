package com.yufan.library.api;

import android.util.Log;

import com.yufan.library.base.BaseApplication;
import com.yufan.library.cache.CacheManager;
import com.yufan.library.util.MD5Util;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
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

        Request request = chain.request().newBuilder()
                .headers(Headers.of(ApiManager.getInstance().getApiHeader(BaseApplication.getInstance())))
                .build();
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
        Log.d("http","request: "+sb.toString());
        Response response = chain.proceed(request);
        ResponseBody responseBody =  response.body();
        MediaType contentType = responseBody.contentType();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE);
        Buffer buffer = source.buffer();

        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        //服务器返回的json原始数据 
        String json = buffer.clone().readString(charset);
        String key=MD5Util.MD5(sb.toString());
        Log.d("http","response: "+json);
        CacheManager.saveObject(BaseApplication.getInstance(),json , key);
       // Log.d("CacheManager", "put cache-> key:" + key + "-> json:" + json);
        return response;
    }
}
