package com.lin.alllib;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lin.alllib.data.EmptyEntity;
import com.lin.alllib.framwork.DebugGod;
import com.lin.alllib.framwork.commander.ILife;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lpds on 2017/7/26.
 */
public abstract class Model {
    protected final String TAG = getClass().getSimpleName();
    private AppCompatActivity activity;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(EmptyEntity emptyEntity) {
        DebugGod.i(TAG, "EmptyEntity");
    }

    protected final void onCreate(AppCompatActivity activity, Bundle savedInstanceState) {
        this.activity = activity;
        init(savedInstanceState);
    }

    protected void onResume() {

    }

    protected void onRestart() {

    }

    protected void onStart() {

    }

    protected void onPause() {

    }

    protected void onStop() {

    }

    protected void onDestroy() {

    }

    protected void onSaveInstanceState(Bundle outState) {

    }

    protected void onCreateBefore() {
    }

    ;

    protected abstract int getContentView();

    protected final AppCompatActivity getActivity() {
        return activity;
    }

    protected abstract void init(Bundle savedInstanceState);

    protected final View findViewById(int id) {
        return activity.findViewById(id);
    }

    protected final void runOnUiThread(Runnable runnable) {
        if (activity != null && !activity.isFinishing()) {
            activity.runOnUiThread(runnable);
        }
    }

}
