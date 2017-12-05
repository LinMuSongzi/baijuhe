package com.yeyuanyuan.web;

import android.content.Intent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by linhui on 2017/12/5.
 */
class Transfer implements InterceptorManager {

    private static InterceptorManager instance;

    static {
        instance = new Transfer();
    }

    private final Set<Interceptor> interceptors = new HashSet<>();
    private final Set<Interceptor> interceptorNetWorks = new HashSet<>();
    private final Set<Completed> callbacks = new HashSet<>();

    private Transfer(){}

    public static InterceptorManager getInstance() {
        return instance;
    }

    @Override
    public void removeInterceptor(Interceptor a) {
        if (a != null && interceptors.contains(a)) {
            interceptors.remove(a);
        }
    }

    @Override
    public void removeNetWorkInterceptor(Interceptor a) {
        if (a != null && interceptorNetWorks.contains(a)) {
            interceptorNetWorks.remove(a);
        }
    }

    @Override
    public void removeCallBack(Completed callback) {
        if (callback != null && callbacks.contains(callback)) {
            callbacks.remove(callback);
        }
    }

    @Override
    public void addNetWorkInterceptor(Interceptor a) {
        if (a != null && !interceptorNetWorks.contains(a)) {
            interceptorNetWorks.add(a);
        }
    }

    @Override
    public void addInterceptor(Interceptor a) {
        if (a != null && !interceptors.contains(a)) {
            interceptors.add(a);
        }
    }

    @Override
    public void addCallBack(Completed callback) {
        if (callback != null && !callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    @Override
    public <T> void onFailure(Call call, IOException e ,RequetEntity<T> requetEntity) {
        final Iterator<Completed> i = callbacks.iterator();
        while (i.hasNext()){
            i.next().onFailure(call,e,requetEntity);
        }
    }

    @Override
    public <T> void onResponse(Call call, Response response,RequetEntity<T> requetEntity) {
        final Iterator<Completed> i = callbacks.iterator();
        while (i.hasNext()){
            i.next().onResponse(call,response,requetEntity);
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
