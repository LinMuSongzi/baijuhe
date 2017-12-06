package com.yeyuanyuan.web;

/**
 * Created by linhui on 2017/12/5.
 */
public class BaseRequetResult implements RequestResult {

    RequetEntity requetEntity;


    @Override
    public void setRequest(RequetEntity request) {
        this.requetEntity = request;
    }

    public RequetEntity getRequet() {
        return requetEntity;
    }

}
