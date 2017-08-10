package com.lin.alllib.framwork.commander;

/**
 * Created by linhui on 2017/8/10.
 * 任何需要设置数据的都继承此接口
 */
public interface IShipment<T> {

    void setData(T t);

}
