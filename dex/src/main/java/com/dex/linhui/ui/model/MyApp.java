package com.dex.linhui.ui.model;

import android.app.Application;

import com.lin.downloadwork.basic.Entrance;

/**
 * Created by linhui on 2017/12/5.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();



        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });

    }
}
