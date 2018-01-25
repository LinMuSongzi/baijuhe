package com.l.ipc;

import android.app.Application;
import android.content.Intent;

import com.l.ipc.imp.PlanService;

/**
 * Created by linhui on 2018/1/25.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(getBaseContext(),PlanService.class));
    }
}
