package com.bjfio.readygo.model.net;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/5.
 */
public interface ImageApis {
    String baseUrl = "http://www.55156.com";

//    String baseUrl = "http://192.168.3.11:8080";
    @GET("/")
    Observable<String> getMainUrl();
//    @GET("/Helloworld/servlet/HelloServlet")
//    Observable<String> getMainUrl();
}
