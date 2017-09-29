package com.fileengine.commander;

import android.graphics.Canvas;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.fileengine.commander.entity.EngineEntity;
import com.fileengine.commander.entity.IFileEntity;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by linhui on 2017/9/28.
 */
public final class Engine implements IEngine {


    private EngineEntity engineEntity = new EngineEntity();
    private AtomicBoolean isPrepare = new AtomicBoolean(false);
    private AtomicBoolean isStop = new AtomicBoolean(false);
    private Handler[] handlers;
    private final Set<IFileEntity> FILES = new HashSet<IFileEntity>() {
        @Override
        public boolean add(IFileEntity o) {
            onExecuteListener.onNext(o);
            return super.add(o);
        }
    };
    private IConvertFile convert;
    private int lastCount = 0;
    private int index = 0;

    public void setConvert(IConvertFile c) {
        this.convert = c;
    }

    public OnExecuteListener onExecuteListener;

    public void Engine() {


    }

    private class EngineRunnable implements Runnable {
        private File f;

        public EngineRunnable(File f) {
            this.f = f;
        }

        @Override
        public void run() {
            findByDir(f);
        }
    }

    @Override
    public void setNextTime(int time) {
        engineEntity.setNextTime(time);
    }

    @Override
    public void setSpeed(int threadCount) {
        engineEntity.setSpeed(threadCount);
    }

    @Override
    public void startScanner(final ScannerFileConfig scannerFileConfig, final OnExecuteListener onExecuteListener) {
        this.onExecuteListener = onExecuteListener;
        this.onExecuteListener.onNext(null);
        engineEntity.setPostfix(scannerFileConfig.getPostfix());
        engineEntity.setFile(new File(scannerFileConfig.getRootFile().getAbsolutePath()));
        handlers[0].post(new EngineRunnable(engineEntity.getFile()));
    }

    @Override
    public void startQueryFile(QueryFileConfig scannerFileConfig, OnExecuteListener onExecuteListener) {

    }

    @Override
    public void startQueryFolder(QueryFileConfig scannerFileConfig, OnExecuteListener onExecuteListener) {

    }

    @Override
    public void stop() {
        isStop.set(true);
    }


    public void prepare() {

        removeAllCallBack();
        isStop.set(false);
        handlers = new Handler[engineEntity.getSpeed()];
        for (int i = 0; i < handlers.length; i++) {
            HandlerThread ht = new HandlerThread(String.valueOf("t" + System.nanoTime()));
            ht.start();
            Handler h = new Handler(ht.getLooper());
            handlers[i] = h;
        }
        if (convert == null) {
            convert = new IConvertFile<IFileEntity>() {
                @Override
                public IFileEntity getEntity(File file) {
                    DefaultFile t = new DefaultFile();
                    t.lenght = file.length();
                    t.name = file.getName();
                    t.filePath = file.getAbsolutePath();
                    t.currentGenre = IFileEntity.SIMPLE_FILE_GENRE;
                    t.postfix = "";
                    return t;
                }
            };
        }
        FILES.clear();
        isPrepare.set(true);
    }

    private void findByDir(File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (isStop.get()) {
                    removeAllCallBack();
                    return false;
                }

                if (pathname.isDirectory()) {
                    synchronized (FILES) {
                        index = index + 1;
                        if (index >= engineEntity.getSpeed()) {
                            index = 0;
                        }
                        handlers[index].post(new EngineRunnable(pathname));
                    }
//                    handlers[new Random().nextInt(engineEntity.getSpeed())].post(new EngineRunnable(pathname));
                } else {
                    Log.i("FILES", new SimpleDateFormat("ss:SSS").format(new Date()) + "  file = " + pathname.getAbsolutePath());
                    if (engineEntity.getPostfix() == null || engineEntity.getPostfix().length == 0) {
                        FILES.add(convert.getEntity(pathname));
                    } else {
                        for (String p : engineEntity.getPostfix()) {
                            if (pathname.getAbsolutePath().endsWith(p)) {
                                FILES.add(convert.getEntity(pathname));
                                break;
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    public synchronized int getLastCount() {
        lastCount++;
        return lastCount;
    }

    private void removeAllCallBack() {

        if (handlers != null) {
            stop();
            for (int i = 0; i < handlers.length; i++) {
                if (handlers[i] != null) {
                    handlers[i].removeCallbacksAndMessages(null);
                    handlers[i].getLooper().quitSafely();
                }
            }
            System.gc();
        }

    }

}
