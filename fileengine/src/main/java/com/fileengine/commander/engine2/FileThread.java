package com.fileengine.commander.engine2;

import android.widget.Toast;

import com.activitys.Scrolling2Activity;
import com.fileengine.commander.FileAppclication;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by linhui on 2017/9/29.
 */
public class FileThread extends Thread {
    private Queue<Runnable> queues = new LinkedList<>();
    private final Object lock = new Object();
    private boolean isStop = false;
    private AtomicBoolean isLock = new AtomicBoolean(false);

    @Override
    public void run() {
        while (true) {
            if (isStop) {
                Toast.makeText(Scrolling2Activity.ACTIVITY, Thread.currentThread().getName() + " 线程停止", Toast.LENGTH_SHORT).show();
                return;
            }
            if (queues.size() == 0) {

                try {

                    synchronized (lock) {
                        if (queues.size() == 0) {
                            isLock.set(true);
                            lock.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isLock.set(false);

            }
            if (queues.size() != 0) {
                Runnable runnable;
                synchronized (lock) {
                    runnable = queues.poll();
                }
                if(runnable!=null){
                    runnable.run();
                }
            }
        }
    }

    FileThread addRunnable(Runnable runnable) {
        queues.add(runnable);
        return this;
    }

    public boolean isLock() {
        return isLock.get();
    }

    public Object getLock() {
        return lock;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
