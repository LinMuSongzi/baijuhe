package com.yeyuanyuan.imp;

import com.yeyuanyuan.commander.Plan;
import com.yeyuanyuan.imp.PlanWrap;

import java.io.File;

/**
 * Created by linhui on 2017/12/4.
 */
public class Factory {


    private Factory(){}

    public static <T> Plan createPlan(Class<T> tClass){
        return new PlanWrap<>(tClass);
    }


}
