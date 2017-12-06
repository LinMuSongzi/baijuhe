package com.yeyuanyuan.web;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by linhui on 2017/12/5.
 */
class Transfer implements InterceptorManager {

    private static InterceptorManager instance;

    static {
        instance = new Transfer();
    }

    private final Set<Interceptor> interceptors = new HashSet<>();
    private final Set<Interceptor> interceptorNetWorks = new HashSet<>();


    final Completed DEFUALT_CALLBACK = new Completed() {
        @Override
        public <T extends RequestResult> void onFailure(Call call, IOException e, RequetParameter<T> requetEntity) {
            requetEntity.isOk = false;
            requetEntity.object.setRequest(requetEntity);
            EventBus.getDefault().post(requetEntity.object);
        }

        @Override
        public <T extends RequestResult> void onResponse(Call call, Response response, RequetParameter<T> requetEntity) {
            requetEntity.isOk = true;
            try {
                if (requetEntity.object != null && !(requetEntity.object instanceof StrEntity)) {
                    requetEntity.object = (T) new Gson().fromJson(response.body().string(), requetEntity.object.getClass());
                }else{
                    StrEntity strEntity = new StrEntity();
                    strEntity.setStrHrml(response.body().string());
                    requetEntity.object = (T) strEntity;
                }
                requetEntity.object.setRequest(requetEntity);
                EventBus.getDefault().post(requetEntity.object);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private Completed callback = DEFUALT_CALLBACK;

    private Transfer(){

    }

    public static InterceptorManager getInstance() {
        return instance;
    }

    @Override
    public void removeInterceptor(Interceptor a) {
        if (a != null && interceptors.contains(a)) {
            interceptors.remove(a);
        }
    }

    @Override
    public void removeNetWorkInterceptor(Interceptor a) {
        if (a != null && interceptorNetWorks.contains(a)) {
            interceptorNetWorks.remove(a);
        }
    }

    @Override
    public void eraseCallBack() {
        if (callback != null) {
            callback = null;
        }
    }

    @Override
    public void addNetWorkInterceptor(Interceptor a) {
        if (a != null && !interceptorNetWorks.contains(a)) {
            interceptorNetWorks.add(a);
        }
    }

    @Override
    public void addInterceptor(Interceptor a) {
        if (a != null && !interceptors.contains(a)) {
            interceptors.add(a);
        }
    }

    @Override
    public void setCallBack(Completed callback) {
        if (callback != null) {
            this.callback = callback;
        }
    }

    @Override
    public boolean hadCallBack() {
        return callback != null;
    }

    @Override
    public <T extends RequestResult> void onFailure(Call call, IOException e ,RequetParameter<T> requetEntity) {
        callback.onFailure(call,e,requetEntity);
    }

    @Override
    public <T extends RequestResult> void onResponse(Call call, Response response,RequetParameter<T> requetEntity) {
        callback.onResponse(call,response,requetEntity);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
