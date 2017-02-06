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
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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
//
//                    if (SystemUtils.isNetworkConnected()) {
//                    int maxAge = 60;
//                    // 有网络时, 不缓存, 最大保存时长为0
//                    response.newBuilder()
//                            .removeHeader("Pragma")
//                            .header("Cache-Control", "public, max-age=" + maxAge)
//                            .build();
//                    } else {
//                        // 无网络时，设置超时为4周
//                        int maxStale = 60 * 60 * 24 * 28;
//                        response.newBuilder()
//                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                                .removeHeader("Pragma")
//                                .build();
//                    }
                    return response;
                }
            };
            //设置缓存
            builder.addNetworkInterceptor(cacheInterceptor);
            builder.addInterceptor(cacheInterceptor);
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
