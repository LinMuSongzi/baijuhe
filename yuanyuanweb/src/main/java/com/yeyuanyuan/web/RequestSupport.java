package com.yeyuanyuan.web;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by linhui on 2017/12/5.
 */
public class RequestSupport {


    public static <T> RequetEntity<T> createPost(Class<T> tClass, String url, Map<String, Object> para, int requestModel) {
        final RequetEntity<T> requetEntity = new RequetEntity<>();
        try {
            requetEntity.t = tClass.newInstance();
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

    public static <T> RequetEntity<T> createGet(Class<T> tClass, String url, Map<String, Object> para) {
        final RequetEntity<T> requetEntity = new RequetEntity<>();
        try {
            requetEntity.t = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        requetEntity.para = para;
        Request.Builder builder = new Request.Builder();
        if (para != null || para.size() > 0) {
            url = RequetEntity.getNewUrl(url, para);
        }
        builder.url(url);
        requetEntity.request = builder.build();
        return requetEntity;
    }

    public static <T> RequetEntity<T> createByJson(Class<T> tClass, String url, String json) {
        final RequetEntity<T> requetEntity = new RequetEntity<>();
        try {
            requetEntity.t = tClass.newInstance();
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
