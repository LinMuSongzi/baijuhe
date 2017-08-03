package com.lin.app.request;

import com.google.gson.JsonObject;
import com.lin.app.data.respone.WeatherRespone;
import com.lin.app.request.retrofit.AnXingApi;
import com.lin.app.request.retrofit.WeatherApi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by lpds on 2017/7/26.
 */
public final class RequestManager{
    private static RequestManager requestManager;
    private static OkHttpClient okHttpClient;
    static {
        okHttpClient = new OkHttpClient();
        requestManager = new RequestManager();
    }

    private AnXingApi haiShenRetrofit;
    private WeatherApi weatherApi;

    public static RequestManager getInstance(){
        return requestManager;
    }

    private RequestManager(){
        haiShenRetrofit = getImp(AnXingApi.class,AnXingApi.PATH);
        weatherApi = getImp(WeatherApi.class,WeatherApi.PATH);
    }

    private <T> T getImp(Class<T> tClass,String path){
        return new Retrofit.Builder().baseUrl(path).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).build().create(tClass);
    }

    public void getAll(Subscriber<WeatherRespone> subscriber){
        config(weatherApi.getAllMsg(WeatherApi.KEY)).subscribe(subscriber);
    }

    private Observable config(Observable observable){
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
