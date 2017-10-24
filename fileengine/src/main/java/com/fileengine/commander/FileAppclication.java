package com.fileengine.commander;

import android.app.Application;

import com.fileengine.commander.engine2.FileThread;

/**
 * Created by linhui on 2017/9/29.
 */
public class FileAppclication extends Application {


    public static FileAppclication appclication;

    @Override
    public void onCreate() {
        super.onCreate();
        this.appclication = this;
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });
    }
}
