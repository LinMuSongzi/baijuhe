package com.mr.huang.framwork;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.mr.huang.framwork.commande.IAppManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by linhui on 2017/9/29.
 */
public class AppManager implements Application.ActivityLifecycleCallbacks,IAppManager{

    private Application application;
    private Stack<Activity> activities;
    private AppManager(){
        activities = new Stack<>();
    }

    private static AppManager appManager = new AppManager();


    public static AppManager getInstance(){
        return appManager;
    }

    public void init(Application application){
        this.application = application;
        this.application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activities.add(activity);
        EventBus.getDefault().register(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        EventBus.getDefault().unregister(activity);
        activities.remove(activity);

    }


    public Activity getCurrentActivity(){
        return activities.peek();
    }


    @Override
    public Application getApplicationContext() {
        return application;
    }
}
