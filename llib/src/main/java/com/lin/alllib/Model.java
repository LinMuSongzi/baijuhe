package com.lin.alllib;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.lin.alllib.data.EmptyEntity;
import com.lin.alllib.framwork.DebugGod;
import com.lin.alllib.framwork.commander.IDeal;
import com.lin.alllib.framwork.commander.ILife;
import com.lin.alllib.framwork.commander.LibModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lpds on 2017/7/26.
 */
public abstract class Model<T> implements LibModel, IDeal<T> {
    protected final String TAG = getClass().getSimpleName();
    private WoodActivity activity;
    /**
     * 是否隐藏状态栏
     */
    private boolean isSystemUiVisibility = true;

    private Toast t;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(EmptyEntity emptyEntity) {
        DebugGod.i(TAG, "EmptyEntity");
    }

    protected final void onCreate(WoodActivity activity, Bundle savedInstanceState) {
        this.activity = activity;
        init(savedInstanceState);
        if (!isSystemUiVisibility) {
            View decorView = getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        if(activity.isHadBar()) {
            getToolbar().setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigationOnClick(v);
                }
            });
        }
//        getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return false;
//            }
//        });
    }

    protected void navigationOnClick(View v) {
        this.onBackPressed();
    }

    protected void onBackPressed() {
        activity.onBackPressed();
    }

    protected void setSystemUiVisibility(boolean isSystemUiVisibility) {
        this.isSystemUiVisibility = isSystemUiVisibility;
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

    protected final void showMsg(String msg) {
        if (t == null) {
            t = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
        } else {
            t.setText(msg);
        }
        t.show();
    }

    protected final void showSnackbar(View view, String msg) {
        if (view != null) {
            Snackbar.make(view,msg,1500).show();
        }
    }

    protected final void showSnackbar(String msg) {
        showSnackbar(findViewById(R.id.root_layout),msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        activity.getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    protected final Toolbar getToolbar() {
        return activity.getToolbar();
    }

    @Override
    public String getValueOfString(String key) {
        return null;
    }

    @Override
    public int getValueOfInteger(String key) {
        return 0;
    }

    @Override
    public float getValueOfFloat(String key) {
        return 0;
    }

    @Override
    public Object getValueOfObject(String key) {
        return null;
    }

    @Override
    public T getAffirmObject(String key) {
        return null;
    }
}
