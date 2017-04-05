package com.bjfio.readygo.model.net;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/5.
 */
public interface ImageApis {
    String baseUrl = "http://www.55156.com/";

    @GET("/")
    Observable<String> getMainUrl();

    @GET("fxtp/")
    Observable<String> getHairUrl();

    @GET("fxtp/list_106_{page}.html")
    Observable<String> getHairUrlPage(@Path("page") String page);

    @GET("gaoqingtaotu/list_42_{page}.html")
    Observable<String> getSexyUrlPage(@Path("page") String page);

    @GET("weimeiyijing/list_48_{page}.html")
    Observable<String> getWeimeiUrlPage(@Path("page") String page);

//    http://www.55156.com/meinvtupian/list_43_2.html
    @GET("meinvtupian/list_43_{page}.html")
    Observable<String> getBeautyUrlPage(@Path("page") String page);

}
