package com.mr.huang.data.entity;

import java.io.Serializable;

/**
 * Created by linhui on 2017/9/30.
 */
public class IntegerEntity implements Serializable {
    int element;

    public IntegerEntity(int element) {
        this.element = element;
    }

    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }
}
