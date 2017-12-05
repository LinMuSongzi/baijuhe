package com.yeyuanyuan.web;



/**
 * Created by linhui on 2017/12/5.
 */
public interface IAccessNetwork {

    int TIME_OUT = 5 * 1000;
    int CACHE_LONG_SIZE = 1024 * 1024 * 50;

    <T> void asyncExecute(RequetEntity<T> o);

    <T> RequetEntity<T> execute(RequetEntity<T> requetEntity);

}
