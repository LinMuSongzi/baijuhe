package com.yeyuanyuan.web;

import android.content.Context;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by linhui on 2017/12/5.
 */
public class Zygote {

    static Context context;

    public static void init(Context context){
        Zygote.context = context;
        ANWrap.getInstance();
    }


    public  static <T extends RequestResult> void execute(RequetParameter<T> requetEntity) {
        ANWrap.getInstance().execute(requetEntity);
    }

    public  static <T extends RequestResult> void asyncExecute(RequetParameter<T> requetEntity) {
        ANWrap.getInstance().asyncExecute(requetEntity);
    }

    public static InterceptorManager getInterceptorMnager() {
        return Transfer.getInstance();
    }

    public static void setCallBack(Completed completed){
        Transfer.getInstance().setCallBack(completed);
    }

    public static <T extends RequestResult> RequetParameter<T> createPost(Class<T> tClass, String url, Map<String, Object> para, int requestModel) {
        final RequetParameter<T> requetEntity = new RequetParameter<>();
        try {
            requetEntity.object = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        requetEntity.para = para;
        Request.Builder builder = new Request.Builder();
        requetEntity.method = RequetParameter.POST;
        builder = builder.url(url);
        if (para != null && para.size() > 0) {
            final FormBody.Builder b = new FormBody.Builder();
            if (para != null && para.size() > 0) {
                for (String key : para.keySet()) {
                    b.add(key, String.valueOf(para.get(key)));
                }
            }
            builder.method(RequetParameter.POST, b.build());
        }
        requetEntity.request = builder.build();
        return requetEntity;
    }

    public static <T extends RequestResult> RequetParameter<T> createGet(Class<T> tClass, String url, Map<String, Object> para) {
        final RequetParameter<T> requetEntity = new RequetParameter<>();
        try {
            requetEntity.object = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        requetEntity.para = para;
        Request.Builder builder = new Request.Builder();
        if (para != null && para.size() > 0) {
            url = RequetParameter.getNewUrl(url, para);
        }
        builder.url(url);
        requetEntity.request = builder.build();
        return requetEntity;
    }

    public static <T extends RequestResult> RequetParameter<T> createPostByJson(Class<T> tClass, String url, String json) {
        final RequetParameter<T> requetEntity = new RequetParameter<>();
        try {
            requetEntity.object = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        requetEntity.method = RequetParameter.POST;
        requetEntity.jsonPara = json;
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.method(RequetParameter.POST, RequestBody.create(RequetParameter.JSON, json));
        requetEntity.request = builder.build();
        return requetEntity;
    }

}
