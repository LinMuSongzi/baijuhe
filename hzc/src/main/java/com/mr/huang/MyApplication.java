package com.mr.huang;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.mr.huang.framwork.AppManager;

/**
 * Created by linhui on 2017/9/29.
 */
public class MyApplication extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        AppManager.getInstance().init(this);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
