package com.yeyuanyuan.imp;

import com.yeyuanyuan.commander.Plan;
import com.yeyuanyuan.commander.Reserve;

/**
 * Created by linhui on 2017/12/4.
 */
public class PlanWrap<T> implements Plan<T>{

    private Plan<T> plan;

    private PlanWrap(Plan<T> plan,Class<T> tClass) {
        if(plan == null){
            plan = new HalePlan(tClass);
        }
    }

    PlanWrap(Class<T> tClass) {
        this(null,tClass);
    }

    @Override
    public Plan where(String[] parameters, String[] values) {
        return plan.where(parameters,values);
    }

    @Override
    public Plan orderBy(boolean isDesc) {
        return plan.orderBy(isDesc);
    }

    @Override
    public Plan column(String[] columns) {
        return plan.column(columns);
    }

    @Override
    public Plan execute(Reserve reserve) {
        return plan.execute(reserve);
    }

    @Override
    public String getTbName() {
        return plan.getTbName();
    }
}
