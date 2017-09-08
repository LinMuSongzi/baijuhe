package com.lin.app.service.binder;

import android.app.Service;
import android.os.Binder;

/**
 * Created by linhui on 2017/8/31.
 */
public class PostmanBinder<S extends Service> extends Binder{

    private S service;

    public PostmanBinder(S service) {
        this.service = service;
    }

    public S getService(){
        return service;
    }

}
