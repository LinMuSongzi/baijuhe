package com.mr.huang.data.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by linhui on 2017/9/29.
 */
public class NumberAttributeEntity implements Serializable,Cloneable{

    String name;

    String createTime;

    int prices;

    Set<IntegerEntity> integers = new HashSet<>();

    public Set<IntegerEntity> getIntegers() {
        return integers;
    }

    public void setIntegers(Set<IntegerEntity> integers) {
        this.integers = integers;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPrices() {
        return prices;
    }

    public void setPrices(int prices) {
        this.prices = prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
