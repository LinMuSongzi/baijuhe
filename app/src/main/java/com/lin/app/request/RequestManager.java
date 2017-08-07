package com.lin.app.request;

import com.lin.alllib.AppGod;
import com.lin.app.data.respone.WeatherRespone;
import com.lin.app.request.retrofit.AnXingApi;
import com.lin.app.request.retrofit.WeatherApi;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by lpds on 2017/7/26.
 */
public final class RequestManager {
    private static RequestManager requestManager;
    private static OkHttpClient okHttpClient;

    static {
        initOkHttpClient();
        requestManager = new RequestManager();
    }

    private AnXingApi haiShenRetrofit;
    private WeatherApi weatherApi;

    public static RequestManager getInstance() {
        return requestManager;
    }

    private static final void initOkHttpClient() {
        okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder().readTimeout(8 * 1000, TimeUnit.SECONDS).connectTimeout(5 * 1000, TimeUnit.SECONDS)
                .writeTimeout(8 * 1000,TimeUnit.SECONDS)
                .cache(new Cache(AppGod.$THIS.getCacheDir(), 1024 * 1024 * 20))
                .build();

    }


    private RequestManager() {
        haiShenRetrofit = getImp(AnXingApi.class, AnXingApi.PATH);
        weatherApi = getImp(WeatherApi.class, WeatherApi.PATH);
    }

    private <T> T getImp(Class<T> tClass, String path) {
        return new Retrofit.
                Builder().
                baseUrl(path).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                client(okHttpClient).build().create(tClass);
    }

    public void getAll(Subscriber<WeatherRespone> subscriber) {
        config(weatherApi.getAllMsg(WeatherApi.KEY)).subscribe(subscriber);
    }

    private Observable config(Observable observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.newThread());
    }

}
