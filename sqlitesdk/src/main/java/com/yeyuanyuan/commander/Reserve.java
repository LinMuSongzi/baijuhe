package com.yeyuanyuan.commander;

/**
 * Created by linhui on 2017/12/4.
 */
public interface Reserve {

    int INSERT = 7774;
    int DELETE = 7277;
    int UPDATE = 7177;
    int QUERY = 7737;

    void result(Object... objects);

    int type();

}
