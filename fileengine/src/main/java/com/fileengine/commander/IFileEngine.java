package com.fileengine.commander;

import java.io.File;

/**
 * Created by linhui on 2017/9/27.
 */
public interface IFileEngine {

    void setNextTime(int time);

    void setSpeed(int threadCount);

    void startScanner(ScannerFileConfig scannerFileConfig,OnExecuteListener onExecuteListener);

    void startQueryFile(QueryFileConfig scannerFileConfig,OnExecuteListener onExecuteListener);

    void startQueryFolder(QueryFileConfig scannerFileConfig,OnExecuteListener onExecuteListener);

    void stop();

//    void pause();
//
//    void resume();

    interface ScannerFileConfig{
        File getRootFile();
        String getPostfix();
    }

    interface QueryFileConfig{
        String getQueryFileName();
    }

    interface QueryFolderConfig{
        String getQueryFolderName();
    }

}
