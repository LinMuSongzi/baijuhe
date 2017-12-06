package com.yeyuanyuan.web;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by linhui on 2017/12/5.
 */
public interface InterceptorManager extends Interceptor,Completed{

    void removeInterceptor(Interceptor a);

    void removeNetWorkInterceptor(Interceptor a);

    void eraseCallBack();

    void addNetWorkInterceptor(Interceptor a);

    void addInterceptor(Interceptor a);

    void setCallBack(Completed callback);

    boolean hadCallBack();

}
