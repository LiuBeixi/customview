package com.github.rxjava.rxbus;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/12.
 */
public abstract class MySubscriber<T> extends Subscriber {
    public abstract void onMyNext(T obj);
    public abstract void onResult(boolean isCompleted,Throwable e);
    @Override
    public void onCompleted() {
        onResult(true,null);
    }
    @Override
    public void onError(Throwable e) {
        onResult(false,e);
    }
    @Override
    public void onNext(Object obj) {
        onMyNext((T)obj);
    }
}
