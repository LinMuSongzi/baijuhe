package com.lin.app.service.commander;

import android.app.Service;

/**
 * Created by linhui on 2017/11/24.
 */
public interface ServiceBoss<T extends Service> {

    String MUSIC_EMPLOYEE = "MUSIC_EMPLOYEE";
    String VIDEO_EMPLOYEE = "VIDEO_EMPLOYEE";
    void attach(T server);
    MusicEmployee getMusicEmployee();
    VideoEmployee getVideoEmployee();
}
