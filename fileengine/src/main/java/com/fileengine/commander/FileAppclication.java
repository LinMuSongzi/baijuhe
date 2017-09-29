package com.fileengine.commander;

import android.app.Application;

/**
 * Created by linhui on 2017/9/29.
 */
public class FileAppclication extends Application {

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
