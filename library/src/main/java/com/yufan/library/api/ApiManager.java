package com.yufan.library.api;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yufan.library.BuildConfig;
import com.yufan.library.Global;
import com.yufan.library.api.config.ApiConfig;
import com.yufan.library.base.BaseApplication;
import com.yufan.library.manager.SPManager;
import com.yufan.library.manager.UserManager;
import com.yufan.library.util.DeviceUtil;
import com.yufan.library.util.Netutil;
import com.yufan.library.util.SIDUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mengfantao on 18/2/27.
 */

public class ApiManager {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;
    private ApiConfig mApiConfig;


    public ApiConfig getApiConfig() {
        return mApiConfig;
    }

    private ApiManager() {

    }

    public void init(int apiType) {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接超时时间        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间
        builder.addInterceptor(new EnhancedCacheInterceptor());
        builder.cookieJar(getCookieJar());
        mApiConfig = new ApiConfig(apiType);
        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mApiConfig.getBaseUrl())
                .build();
    }

    private static class SingletonHolder {
        private static final ApiManager INSTANCE = new ApiManager();
    }

    protected static CookieJar getCookieJar() {
        return new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                String mainHost = url.host();
                List<Cookie> resultCookies = new ArrayList<>();
                Cookie.Builder builder = new Cookie.Builder();
                Cookie cookie_tmpKey = builder
                        .name("token")
                        .value(UserManager.getInstance().getToken())
                        .domain(mainHost)
                        .build();
                resultCookies.add(cookie_tmpKey);
                return resultCookies != null ? resultCookies : new ArrayList<Cookie>();
            }
        };
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return
     */
    public static ApiManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }


    public static EnhancedCall getCall(Call call) {
        return new EnhancedCall(call);
    }


    /**
     * 获取HTTP header信息
     *
     * @return
     */
    public Map<String, String> getApiHeader(Context context) {
        LinkedHashMap apiHeaders = new LinkedHashMap<String, String>();
        apiHeaders.put("Accept-Language", Locale.getDefault().toString());
        apiHeaders.put("Connection", "Keep-Alive");
        apiHeaders.put("Content-Type", "application/x-www-form-urlencoded");
        apiHeaders.put("User-Agent", "");
        apiHeaders.put("LK-App-Version", DeviceUtil.VersionName(context) + "_" + DeviceUtil.VersionCode(context));//app版本  1.0.0_101_10
        apiHeaders.put("LK-Device-Id", DeviceUtil.IMEI(context));//imei
        apiHeaders.put("LK-Network-Type", Netutil.GetNetworkType(context));//网络类型
        apiHeaders.put("LK-Vendor", Build.MANUFACTURER); //厂商
        apiHeaders.put("LK-OS-Type", "Android");//系统类型
        apiHeaders.put("LK-OS-Version", Build.VERSION.SDK_INT + "");//系统版本
        apiHeaders.put("LK-Device-Model", Build.DEVICE);//设备型号
        apiHeaders.put("LK-CPU", Build.CPU_ABI);//CPU 架构
        apiHeaders.put("LK-Sid", SIDUtil.getSID(context));//sid
        apiHeaders.put("LK-App-Id", "com.yushi.leke");//包名
        return apiHeaders;
    }
}
