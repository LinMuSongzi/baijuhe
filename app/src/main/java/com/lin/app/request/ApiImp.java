package com.lin.app.request;

import com.lin.alllib.data.respone.CityRespone;
import com.lin.alllib.framwork.RequestManager;
import com.lin.app.request.retrofit.WeatherApi;

import rx.Subscriber;

/**
 * Created by linhui on 2017/8/8.
 */
public class ApiImp {

    public static void getAllCity(Subscriber<CityRespone> subscriber){
        RequestManager.getInstance()
        .config(RequestManager.getInstance().
                getApi(WeatherApi.class).
                getAllMsg(WeatherApi.KEY),
                subscriber);
    }

}
