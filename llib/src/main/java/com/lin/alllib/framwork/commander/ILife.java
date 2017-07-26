package com.lin.alllib.framwork.commander;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by lpds on 2017/7/26.
 */
public interface ILife {

    void onCreate(Activity activity, Bundle savedInstanceState);

    void onResume(Activity activity);

    void onRestart();

    void onStart(Activity activity);

    void onPause(Activity activity);

    void onStop(Activity activity);

    void onDestroy(Activity activity);

    void onSaveInstanceState(Activity activity, Bundle outState);
}
