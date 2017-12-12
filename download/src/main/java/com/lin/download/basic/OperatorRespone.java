package com.lin.download.basic;

/**
 * Created by linhui on 2017/12/11.
 */
public interface OperatorRespone<T> {

    int getCode();

    void success(T object);

    void error();

}
