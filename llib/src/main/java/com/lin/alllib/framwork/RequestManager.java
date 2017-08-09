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
    public static final String K_API = "api_s";
    private static final String TAG = "lib_RequestManager";
    private static RequestManager requestManager;
    private static OkHttpClient okHttpClient;

    static {
        requestManager = new RequestManager();
        initOkHttpClient();
//        requestManager.init();
    }

    private List<IRequestInterceptor> iRequestInterceptors;
    private Map<Class, Object> apis = new Hashtable<>();
    private Observable<IRequestInterceptor> interceptorObservable;

    private RequestManager() {
        iRequestInterceptors = new LinkedList<>();
        interceptorObservable = Observable.from(iRequestInterceptors);
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    private static final void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder().readTimeout(8 * 1000, TimeUnit.SECONDS).connectTimeout(5 * 1000, TimeUnit.SECONDS)
                .writeTimeout(8 * 1000, TimeUnit.SECONDS)
                .cache(new Cache(AppGod.$THIS.getCacheDir(), 1024 * 1024 * 20))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(final Chain chain) throws IOException {
                        DebugGod.i(TAG, "request url : " + chain.request().url());
                        return chain.proceed(chain.request());
                    }
                }).build();

    }

    public <T> void initApi(Class<T> tClass, String str) {
        if (!apis.containsKey(tClass)) {
            apis.put(tClass, getImp(tClass, str));
            DebugGod.i(TAG, tClass.getSimpleName() + " api init ");
        }
    }

    public void addRequestInterceptor(IRequestInterceptor iRequestInterceptor) {
        iRequestInterceptors.add(iRequestInterceptor);
    }

    public void removeRequestInterceptor(IRequestInterceptor iRequestInterceptor) {
        iRequestInterceptors.remove(iRequestInterceptor);
    }

    public <T> T getApi(Class<T> tClass) {
        DebugGod.i(TAG, tClass.getSimpleName() + " api get ");
        return (T) apis.get(tClass);
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
                subscriber.onNext(t);
            }
        });
    }
}
