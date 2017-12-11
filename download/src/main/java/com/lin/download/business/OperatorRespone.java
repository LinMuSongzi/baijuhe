package com.lin.download.business;

/**
 * Created by linhui on 2017/12/11.
 */
public interface OperatorRespone {

    int getCode();

    int success(Object object);

    int error();

}
