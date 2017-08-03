package com.lin.alllib.framwork.manager;

import android.app.Activity;
import android.os.Bundle;

import com.lin.alllib.AppLife;
import com.lin.alllib.WoodActivity;
import com.lin.alllib.framwork.commander.ILife;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Created by lpds on 2017/7/26.
 */
public class ActivityManager implements ILife {
    private static ActivityManager activityManager;
    static {
        activityManager = new ActivityManager();
    }
    private ActivityManager(){
        activities = new Stack<>();
        AppLife.getInstance().add(this);

    }

    public static ActivityManager getInstance(){
        return activityManager;
    }

    private Stack<Activity> activities;

    public Activity getTopActivity(){
        return activities.peek();
    }

    public <T extends Activity> Collection<T> getActivity(Class<T> tClass){
        Collection<T> collection = new LinkedList<>();
        for(Activity activity : activities){
            if(tClass.isInstance(activity)){
                collection.add((T) activity);
            }
        }
        return collection;
    }

    @Override
    public void onCreate(Activity activity, Bundle savedInstanceState) {
        activities.push(activity);
        if(activity instanceof WoodActivity) {
            EventBus.getDefault().register(((WoodActivity) activity).getModel());
        }
    }

    @Override
    public void onResume(Activity activity) {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onStart(Activity activity) {

    }

    @Override
    public void onPause(Activity activity) {

    }

    @Override
    public void onStop(Activity activity) {

    }

    @Override
    public void onDestroy(Activity activity) {
        if(activity instanceof WoodActivity) {
            EventBus.getDefault().unregister(((WoodActivity) activity).getModel());
        }
        activities.remove(activity);
    }

    @Override
    public void onSaveInstanceState(Activity activity, Bundle outState) {

    }
}
