package com.lin.alllib.framwork.commander;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by linhui on 2017/8/8.
 */
public interface IRequestInterceptor {


    boolean onRequest(Interceptor.Chain request);


    boolean onResponse(Object o);

}
