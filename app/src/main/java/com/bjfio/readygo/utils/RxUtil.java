package com.bjfio.readygo.utils;

import android.text.TextUtils;


import com.bjfio.readygo.model.net.ApiException;
import com.bjfio.readygo.model.net.JavaHttpResponse;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: RxUtil
 * Creator: yxc
 * date: 2016/9/21 18:47
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {

                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<JavaHttpResponse<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<JavaHttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<JavaHttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<JavaHttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(JavaHttpResponse<T> javaHttpResponse) {
                        if (javaHttpResponse.getStatus() == 200) {
                            return createData(javaHttpResponse.getData());
                        } else if (!TextUtils.isEmpty(javaHttpResponse.getDesc())) {
                            return Observable.error(new ApiException("*" + javaHttpResponse.getDesc()));
                        } else {
                            return Observable.error(new ApiException("*" + "服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
