package com.yeyuanyuan.web;


import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by linhui on 2017/12/5.
 */
public interface IAccessNetwork<T extends RequestResult> {

    int TIME_OUT = 5 * 1000;
    int CACHE_LONG_SIZE = 1024 * 1024 * 50;

    void asyncExecute(RequetEntity<T> o);

    void execute(RequetEntity<T> requetEntity);



}
