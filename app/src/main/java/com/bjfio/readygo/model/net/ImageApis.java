package com.bjfio.readygo.model.net;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/5.
 */
public interface ImageApis {
    String baseUrl = "http://www.55156.com";

    @GET("/")
    Observable<String> getMainUrl();
}
