package com.lin.alllib;

import android.app.Application;

import com.lin.alllib.framwork.ThreadException;
import com.lin.alllib.framwork.manager.ActivityManager;

/**
 * Created by lpds on 2017/7/26.
 */
public class AppGod extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityManager.getInstance();
        registerActivityLifecycleCallbacks(AppLife.getInstance().getActivityLifecycleCallbacks());
        Thread.setDefaultUncaughtExceptionHandler(new ThreadException());
    }
}
