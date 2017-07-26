package com.lin.alllib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lin.alllib.framwork.commander.ILife;
import com.lin.alllib.framwork.commander.CommanderLife;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by lpds on 2017/7/26.
 */
public class AppLife implements Application.ActivityLifecycleCallbacks,CommanderLife{
    private static AppLife appLife;
    static{
        appLife = new AppLife();
    }
    private AppLife(){
        lifeSet = new LinkedHashSet<>();
    }
    public static CommanderLife getInstance(){return appLife;}

    private Set<ILife> lifeSet;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        for(ILife iLife : lifeSet){
            iLife.onCreate(activity,savedInstanceState);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        for(ILife iLife : lifeSet){
            iLife.onStart(activity);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        for(ILife iLife : lifeSet){
            iLife.onResume(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        for(ILife iLife : lifeSet){
            iLife.onPause(activity);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        for(ILife iLife : lifeSet){
            iLife.onStop(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        for(ILife iLife : lifeSet){
            iLife.onSaveInstanceState(activity,outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        for(ILife iLife : lifeSet){
            iLife.onDestroy(activity);
        }
    }

    @Override
    public void add(ILife life) {
        lifeSet.add(life);
    }

    @Override
    public void remove(ILife life) {
        lifeSet.remove(life);
    }

    @Override
    public Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return this;
    }
}
