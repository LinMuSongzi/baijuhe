package com.fileengine.commander;

import android.graphics.Canvas;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

import com.fileengine.commander.entity.EngineEntity;
import com.fileengine.commander.entity.IFileEntity;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
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


    private EngineEntity engineEntity;
    private AtomicBoolean isPrepare = new AtomicBoolean(false);
    private AtomicBoolean isStop = new AtomicBoolean(false);
    private Handler[] handlers;
    public OnExecuteListener onExecuteListener;
    private final Set<IFileEntity> FILES = new LinkedHashSet<IFileEntity>() {
        @Override
        public boolean add(IFileEntity o) {
            onExecuteListener.onNext(o, FILES.size() + 1);
            return super.add(o);
        }
    };
    private IConvertFile convert;
    private int lastCount = 0;
    private int index = 0;

    @Override
    public void init(OnExecuteListener onExecuteListener) {
        this.onExecuteListener = onExecuteListener;
    }

    @Override
    public void prepare(EngineEntity engineEntity) {
        removeAllCallBack();
        this.engineEntity = engineEntity;
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

    public void setConvert(IConvertFile c) {
        this.convert = c;
    }

    @Override
    public void startScanner() {
        if (start()) {
            onExecuteListener.onStart();
            handlers[0].post(new EngineRunnable(Environment.getExternalStorageDirectory()));
        }
    }

    @Override
    public void startQueryFile() {
        if (Environment.getExternalStorageDirectory().getAbsolutePath()
                .equals(engineEntity.getFile().getAbsolutePath())) {
            startScanner();
        } else if (start()) {
            handlers[0].post(new EngineRunnable(engineEntity.getFile()));
        }
    }

    @Override
    public void startQueryFolder() {
    }


    private boolean start() {
        if (isPrepare.get()) {
            isStop.set(false);
            return true;
        }
        return false;
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
    public void stop() {
        isStop.set(true);
        isPrepare.set(false);
    }

    private void findByDir(File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (isStop.get()) {
                    removeAllCallBack();
                    return false;
                }

                if (pathname.isDirectory() && pathname.canRead()) {
                    synchronized (FILES) {
                        index = index + 1;
                        if (index >= engineEntity.getSpeed()) {
                            index = 0;
                        }
                        handlers[index].post(new EngineRunnable(pathname));
                    }
//                    handlers[new Random().nextInt(engineEntity.getSpeed())].post(new EngineRunnable(pathname));
                } else {
                    try {
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
                    }catch (Exception ex){
                        ex.printStackTrace();
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

    private synchronized void removeAllCallBack() {

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
