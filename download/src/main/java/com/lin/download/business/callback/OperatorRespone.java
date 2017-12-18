package com.lin.download.business.callback;

import java.lang.annotation.Documented;

/**
 * Created by linhui on 2017/12/11.
 * 获得数据库操作的操作回调
 */
@Deprecated
public interface OperatorRespone<T> {

    int getCode();

    void success(T object);

    void error();

}
