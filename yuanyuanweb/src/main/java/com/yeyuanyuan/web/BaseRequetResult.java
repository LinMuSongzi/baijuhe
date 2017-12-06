package com.yeyuanyuan.web;

/**
 * Created by linhui on 2017/12/5.
 */
public class BaseRequetResult implements RequestResult {

    RequetParameter requetParameter;


    @Override
    public void setRequest(RequetParameter request) {
        this.requetParameter = request;
    }

    public RequetParameter getRequet() {
        return requetParameter;
    }

}
