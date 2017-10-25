package com.lin.app.common.lottery;

/**
 * Created by linhui on 2017/10/25.
 */
public interface IOrderEntity extends Identity,ICreateTime {

    int getByType();

    long getBytagId();

    long getByMoney();

}
