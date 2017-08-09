package com.lin.alllib.framwork;

import com.lin.alllib.AppGod;
import com.lin.alllib.R;
import com.lin.alllib.data.respone.BaseRespone;
import com.lin.alllib.framwork.commander.IRequestInterceptor;

import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lpds on 2017/7/26.
 */
public final class RequestManager {
    private static final String TAG = "lib_RequestManager";
    private static RequestManager requestManager;
    private static OkHttpClient okHttpClient;
    public static final String K_API = "api_s";
    private IRequestInterceptor iRequestInterceptor;

    private Map<Class, Object> apis = new Hashtable<>();

    static {
        requestManager = new RequestManager();
        initOkHttpClient();
//        requestManager.init();
    }

    private RequestManager() {
    }

    public <T> void initApi(Class<T> tClass, String str) {
        if (!apis.containsKey(tClass)) {
            apis.put(tClass, getImp(tClass, str));
            DebugGod.i(TAG, tClass.getSimpleName() + " api init ");
        }
    }

    public void setRequestInterceptor(IRequestInterceptor iRequestInterceptor) {
        this.iRequestInterceptor = iRequestInterceptor;
    }

    public void removeRequestInterceptor(IRequestInterceptor iRequestInterceptor) {
        this.iRequestInterceptor = null;
    }

    public <T> T getApi(Class<T> tClass) {
        DebugGod.i(TAG, tClass.getSimpleName() + " api get ");
        return (T) apis.get(tClass);
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    private static final void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder().readTimeout(8 * 1000, TimeUnit.SECONDS).connectTimeout(5 * 1000, TimeUnit.SECONDS)
                .writeTimeout(8 * 1000, TimeUnit.SECONDS)
                .cache(new Cache(AppGod.$THIS.getCacheDir(), 1024 * 1024 * 20))
                .build();

    }


    private <T> T getImp(Class<T> tClass, String path) {
        return new Retrofit.
                Builder().
                baseUrl(path).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                client(okHttpClient).build().create(tClass);
    }


    public <T> Subscription config(Observable observable, final Subscriber<T> subscriber) {
        return observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread()).subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {
                subscriber.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                subscriber.onError(e);
            }

            @Override
            public void onNext(final T t) {
                if (iRequestInterceptor != null) {
                    if (iRequestInterceptor.onResponse(t)) {
                        subscriber.onNext(t);
                    }else{
                        final BaseRespone r = (BaseRespone) t;
                        DebugGod.i(TAG, "data is interceptor ,url = " + r.getUrl());
                    }
                }else{
                    subscriber.onNext(t);
                }

            }
        });
    }
}
