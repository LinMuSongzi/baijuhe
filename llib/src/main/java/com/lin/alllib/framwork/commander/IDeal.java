package com.lin.alllib.framwork.commander;

/**
 * Created by linhui on 2017/8/24.
 * 用于沟通交换数据的接口
 */
public interface IDeal<T>{

    String getValueOfString(String key);

    int getValueOfInteger(String key);

    float getValueOfFloat(String key);

    Object getValueOfObject(String key);

    T getAffirmObject(String key);
}
