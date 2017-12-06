package com.lin.app.common;

import android.util.Log;

import rx.Subscriber;

/**
 * Created by linhui on 2017/8/9.
 */
public abstract class SubscriberImp<T> extends Subscriber<T> {

    private static final String TAG = "retrofit_Subscriber";

    @Override
    public void onCompleted() {
        Log.i(TAG, "onCompleted: ");
    }

    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError: ");
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        onNext2(t);
    }

    public abstract void onNext2(T t);

}
