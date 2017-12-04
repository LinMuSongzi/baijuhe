package com.yeyuanyuan.commander;

/**
 * Created by linhui on 2017/12/4.
 */
public interface Plan<T> {


    Plan where(String[] parameters,String[] values);

    Plan orderBy(boolean isDesc);

    Plan column(String[] columns);

    Plan execute(Reserve reserve);

    String getTbName();

}
