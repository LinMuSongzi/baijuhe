package com.lin.download;

import android.app.Application;
import android.content.Context;
import android.widget.ZoomButton;

import com.yeyuanyuan.web.Zygote;

import y.com.sqlitesdk.framework.IfeimoSqliteSdk;

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
