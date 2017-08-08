package com.lin.alllib;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.lin.alllib.framwork.RequestManager;
import com.lin.alllib.framwork.ThreadException;
import com.lin.alllib.framwork.manager.ActivityManager;

/**
 * Created by lpds on 2017/7/26.
 */
public class AppGod extends MultiDexApplication {

    public static Context $THIS;

    @Override
    public void onCreate() {
        super.onCreate();
        $THIS = this;
        ActivityManager.getInstance();
        RequestManager.getInstance();
        registerActivityLifecycleCallbacks(AppLife.getInstance().getActivityLifecycleCallbacks());
        Thread.setDefaultUncaughtExceptionHandler(new ThreadException());
    }
}
