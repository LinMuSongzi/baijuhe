package com.lin.alllib.framwork.commander;

import android.app.Application;

/**
 * Created by lpds on 2017/7/26.
 */
public interface CommanderLife {

    void add(ILife life);

    void remove(ILife life);

    Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks();

}
