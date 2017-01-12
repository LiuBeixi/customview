package com.github.rxjava.rxbus;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/1/12.
 */
public abstract class SimpleSubscriber<T> extends Subscriber {
    public abstract void onMyNext(T obj);
    public void onResult(boolean isCompleted,Throwable e) {
    }
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
