package com.yeyuanyuan.web;

import android.app.Application;
import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by linhui on 2017/12/5.
 */
public class Zygote {

    static Context context;

    public static void init(Context context){
        Zygote.context = context;
        ANWrap.getInstance();
    }



    public static InterceptorManager getInterceptorMnager() {
        return Transfer.getInstance();
    }
}
