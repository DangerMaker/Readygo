package com.bjfio.readygo.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */
public class RxPresenter<T extends EmptyView> implements BasePresenter<T> {

    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
        }
    }

    protected void addSubscrebe(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
        if(mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
        }
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}