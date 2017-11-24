package com.lin.app.service.binder;

import android.app.Service;

import com.lin.app.service.commander.ServiceBoss;

/**
 * Created by linhui on 2017/11/24.
 */
public interface ServerSupport<T extends Service> extends ServiceBoss<T>{

    Service getService();

}
