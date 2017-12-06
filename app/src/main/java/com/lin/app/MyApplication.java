package com.lin.app;

import com.lin.alllib.LibApplication;
import com.lin.alllib.framwork.RequestManager;
import com.lin.app.request.retrofit.AnXingApi;
import com.lin.app.request.retrofit.LpdsApi;
import com.lin.app.request.retrofit.WeatherApi;

/**
 * Created by linhui on 2017/8/8.
 */
public class MyApplication extends LibApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        {
            RequestManager.getInstance().initApi(WeatherApi.class, WeatherApi.PATH);
            RequestManager.getInstance().initApi(AnXingApi.class, AnXingApi.PATH);
            RequestManager.getInstance().initApi(LpdsApi.class, LpdsApi.PATH);
        }
    }
}
