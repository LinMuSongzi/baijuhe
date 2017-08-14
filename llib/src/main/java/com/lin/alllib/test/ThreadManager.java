package com.lin.alllib.test;

/**
 * Created by linhui on 2017/8/13.
 */
public class ThreadManager extends Thread{


    private static ThreadManager ThreadManager;

    static {
        ThreadManager = new ThreadManager();
    }

    private ThreadCommunication threadCommunication;

    private ThreadManager(){

    }

    public static ThreadManager getInstance(){
        return ThreadManager;
    }

    public void run(){
        while(true){

            synchronized (ThreadManager){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void communication(ThreadCommunication threadCommunication){
        this.threadCommunication = threadCommunication;
    }

}
