package com.lin.download;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ZoomButton;

import com.lin.download.basic.Entrance;
import com.liulishuo.filedownloader.FileDownloader;
import y.com.sqlitesdk.framework.IfeimoSqliteSdk;

/**
 * Created by linhui on 2017/12/5.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Entrance.init(this);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });

    }
}
