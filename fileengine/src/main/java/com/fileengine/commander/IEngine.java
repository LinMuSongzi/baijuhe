package com.fileengine.commander;

import java.io.File;

/**
 * Created by linhui on 2017/9/27.
 */
public interface IEngine {
    int DEFAULT_TIME = 1000;
    int DEFAULT_THREAD_COUNT = 5;
    void prepare();
    void setConvert(IConvertFile c);
    void setNextTime(int time);
    void setSpeed(int threadCount);
    void startScanner(ScannerFileConfig scannerFileConfig,OnExecuteListener onExecuteListener);
    void startQueryFile(QueryFileConfig scannerFileConfig,OnExecuteListener onExecuteListener);
    void startQueryFolder(QueryFileConfig scannerFileConfig,OnExecuteListener onExecuteListener);
    void stop();
    interface ScannerFileConfig{
        File getRootFile();
        String[] getPostfix();
    }
    interface QueryFileConfig{
        String getQueryFileName();
    }
    interface QueryFolderConfig{
        String getQueryFolderName();
    }
}
