package com.lin.download.basic;

/**
 * Created by linhui on 2017/12/11.
 */
public interface Plan extends Runnable{
    int AUTO_RETRY_TIMES = 1;
    @Deprecated
    void reset();
    void pause();
    void download();
    void delete(boolean isDeleteFile);
    int getModelId();
    boolean isRunning();
}
