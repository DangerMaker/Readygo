package com.bjfio.readygo.model.net;

import com.bjfio.readygo.BuildConfig;
import com.bjfio.readygo.app.Constants;
import com.bjfio.readygo.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Description: RetrofitHelper
 * Creator: yxc
 * date: 2016/9/21 10:03
 */
public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private static NewsApis videoApi;
    private static ImageApis imageApis;

    static boolean flag = true;

    public static NewsApis getNewsApi() {
        initOkHttp();
        if (videoApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(NewsApis.HOST)
                    .addConverterFactory(StringConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            videoApi = retrofit.create(NewsApis.class);
        }
        return videoApi;
    }

    public static ImageApis getImageApi() {
        initOkHttp();
        if (imageApis == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ImageApis.baseUrl)
                    .addConverterFactory(StringConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            imageApis = retrofit.create(ImageApis.class);
        }
        return imageApis;
    }

    private static void initOkHttp() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                builder.addInterceptor(loggingInterceptor);
            }
            File cacheFile = new File(Constants.PATH_CACHE);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
            Interceptor cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!SystemUtils.isNetworkConnected()) {
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();
                    }
                    Response response = chain.proceed(request);
                    return response;
                }
            };

//            Interceptor headerInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {

//                    Request request = chain.request();
//                    Request.Builder builder = request.newBuilder();
//                    request = builder.addHeader("cookie", "cookie1").build();

//                    Response response = chain.proceed(request);

//                    if(flag){
//                        Request.Builder b = request.newBuilder();
//                        request = b.removeHeader("cookie")
//                                .addHeader("cookie", "cookie2")
//                                .build();
//                        response = chain.proceed(request);
//                        flag = false;
//                    }

//                    return response;
//                }
//            };

            //设置缓存
//            builder.addNetworkInterceptor(cacheInterceptor);
            builder.addInterceptor(cacheInterceptor);
//            builder.addInterceptor(headerInterceptor);
            builder.cache(cache);
            //设置超时
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(10, TimeUnit.SECONDS);
            //错误重连
            builder.retryOnConnectionFailure(true);
            okHttpClient = builder.build();
        }
    }
}
