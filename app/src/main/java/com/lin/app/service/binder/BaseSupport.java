package com.lin.app.service.binder;

import android.app.Service;

/**
 * Created by linhui on 2017/11/24.
 */
abstract class BaseSupport<T extends Service> implements ServerSupport<T>{
    protected T service;
    @Override
    public T getService() {
        return service;
    }

    @Override
    public void attach(T server) {
        this.service = server;
        attachAfter();
    }

    protected abstract void attachAfter();
}
