package com.yeyuanyuan.web;


/**
 * Created by linhui on 2017/12/5.
 */
public interface IAccessNetwork<T extends RequestResult> {

    int TIME_OUT = 5 * 1000;
    int CACHE_LONG_SIZE = 1024 * 1024 * 50;

    void asyncExecute(RequetParameter<T> o);

    void execute(RequetParameter<T> requetEntity);



}
