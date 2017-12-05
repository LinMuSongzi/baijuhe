package com.yeyuanyuan.web;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by linhui on 2017/12/5.
 */
public interface Completed {

    <T> void onFailure(Call call, IOException e , RequetEntity<T> requetEntity);

    <T> void onResponse(Call call, Response response, RequetEntity<T> requetEntity);

}
