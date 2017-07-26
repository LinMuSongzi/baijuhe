package com.lin.alllib.framwork;

/**
 * Created by lpds on 2017/7/26.
 */
public class ThreadException implements Thread.UncaughtExceptionHandler{




    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(e.getMessage());
    }
}
