package com.lin.app;

import com.lin.alllib.AppGod;
import com.lin.alllib.framwork.RequestManager;
import com.lin.app.request.retrofit.AnXingApi;
import com.lin.app.request.retrofit.WeatherApi;

/**
 * Created by linhui on 2017/8/8.
 */
public class MyApplication extends AppGod {
    @Override
    public void onCreate() {
        super.onCreate();
        {
            RequestManager.getInstance().initApi(WeatherApi.class, WeatherApi.PATH);
            RequestManager.getInstance().initApi(AnXingApi.class, AnXingApi.PATH);
        }
    }
}
