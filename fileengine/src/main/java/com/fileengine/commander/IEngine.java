package com.fileengine.commander;

import com.fileengine.commander.entity.EngineEntity;

import java.io.File;

/**
 * Created by linhui on 2017/9/27.
 */
public interface IEngine {
    int DEFAULT_TIME = 1000;
    int DEFAULT_THREAD_COUNT = 5;
    void init(OnExecuteListener onExecuteListener);
    void prepare(EngineEntity engineEntity);
    void setConvert(IConvertFile c);
    void startScanner();
    void startQueryFile();
    void startQueryFolder();
    void stop();
}
