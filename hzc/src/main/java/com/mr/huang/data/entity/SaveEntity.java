package com.mr.huang.data.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by linhui on 2017/9/29.
 */
public class SaveEntity {
    int prices;
    String name;
    Set<Integer> integers = new HashSet<>();


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

    public Set<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(Set<Integer> integers) {
        this.integers = integers;
    }
}
