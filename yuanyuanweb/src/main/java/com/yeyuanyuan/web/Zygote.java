package com.yeyuanyuan.web;

import android.app.Application;
import android.content.Context;

import java.io.IOException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
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


    public  static <T extends RequestResult> void execute(RequetEntity<T> requetEntity) {
        ANWrap.getInstance().execute(requetEntity);
    }

    public  static <T extends RequestResult> void asyncExecute(RequetEntity<T> requetEntity) {
        ANWrap.getInstance().asyncExecute(requetEntity);
    }

    public static InterceptorManager getInterceptorMnager() {
        return Transfer.getInstance();
    }

    public static void setCallBack(Completed completed){
        Transfer.getInstance().setCallBack(completed);
    }

    public static <T extends RequestResult> RequetEntity<T> createPost(Class<T> tClass, String url, Map<String, Object> para, int requestModel) {
        final RequetEntity<T> requetEntity = new RequetEntity<>();
        try {
            requetEntity.object = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        requetEntity.para = para;
        Request.Builder builder = new Request.Builder();
        requetEntity.method = RequetEntity.POST;
        builder = builder.url(url);
        if (para != null && para.size() > 0) {
            final FormBody.Builder b = new FormBody.Builder();
            if (para != null && para.size() > 0) {
                for (String key : para.keySet()) {
                    b.add(key, String.valueOf(para.get(key)));
                }
            }
            builder.method(RequetEntity.POST, b.build());
        }
        requetEntity.request = builder.build();
        return requetEntity;
    }

    public static <T extends RequestResult> RequetEntity<T> createGet(Class<T> tClass, String url, Map<String, Object> para) {
        final RequetEntity<T> requetEntity = new RequetEntity<>();
        try {
            requetEntity.object = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        requetEntity.para = para;
        Request.Builder builder = new Request.Builder();
        if (para != null && para.size() > 0) {
            url = RequetEntity.getNewUrl(url, para);
        }
        builder.url(url);
        requetEntity.request = builder.build();
        return requetEntity;
    }

    public static <T extends RequestResult> RequetEntity<T> createByJson(Class<T> tClass, String url, String json) {
        final RequetEntity<T> requetEntity = new RequetEntity<>();
        try {
            requetEntity.object = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        requetEntity.method = RequetEntity.POST;
        requetEntity.jsonPara = json;
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.method(RequetEntity.POST, RequestBody.create(RequetEntity.JSON, json));
        requetEntity.request = builder.build();
        return requetEntity;
    }

}
