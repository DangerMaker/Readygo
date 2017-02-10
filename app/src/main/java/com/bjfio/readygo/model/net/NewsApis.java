// (c)2016 Flipboard Inc, All Rights Reserved.

package com.bjfio.readygo.model.net;


import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Description: NewsApis
 * Creator: yxc
 * date: 2016/9/8 14:05
 */

public interface NewsApis {
    String HOST = "http://m.27270.com/ent/rentiyishu/";

    @GET("list_32_{page}.html")
    Observable<String> getHtml(@Path("page") String page);

    @GET
    Observable<String> getDataFromUrl(@Url String url);
}