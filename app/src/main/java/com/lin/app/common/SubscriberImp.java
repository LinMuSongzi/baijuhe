package com.lin.app.common;

import rx.Subscriber;

/**
 * Created by linhui on 2017/8/9.
 */
public abstract class SubscriberImp<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T t) {
        onNext2(t);
    }

    public abstract void onNext2(T t);

}
