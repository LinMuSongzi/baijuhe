package com.lin.alllib.test;

import android.security.keystore.UserNotAuthenticatedException;

import java.util.ArrayList;
import java.util.List;

import rx.internal.util.ActionNotificationObserver;

/**
 * Created by linhui on 2017/8/10.
 */
public class LisrTest extends Thread implements Thread.UncaughtExceptionHandler {

    public static void main(String args[]) throws InterruptedException {
        LisrTest oneThread = new LisrTest();
        Thread.setDefaultUncaughtExceptionHandler(oneThread);
        while(true){
            synchronized (oneThread){
                oneThread.wait();
            }
        }




    }

    @Override
    public void run() {

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
