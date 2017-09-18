package com.lin.alllib;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.lin.alllib.framwork.RequestManager;
import com.lin.alllib.framwork.ThreadException;

/**
 * Created by lpds on 2017/7/26.
 */
public class LibApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ((AppLife)AppLife.getInstance()).setApplication(this);
        RequestManager.getInstance();
        Thread.setDefaultUncaughtExceptionHandler(new ThreadException());
    }
}
