package com.lin.alllib.framwork;

import com.lin.alllib.data.respone.CityRespone;
import com.lin.alllib.framwork.commander.IRequestInterceptor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.Interceptor;

/**
 * Created by linhui on 2017/8/8.
 */
public class PhoneLocationManager {

    private static final String TAG = "liv_LocationManager";

    private static PhoneLocationManager phoneLocationManager;

    {
        phoneLocationManager = new PhoneLocationManager();
    }

    public static final PhoneLocationManager getInstance() {
        return phoneLocationManager;
    }

    public PhoneLocationManager() {

    }
}
