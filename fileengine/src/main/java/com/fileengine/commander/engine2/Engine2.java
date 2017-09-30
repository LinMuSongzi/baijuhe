package com.fileengine.commander.engine2;

import android.util.Log;

import com.fileengine.R;
import com.fileengine.commander.DefaultFile;
import com.fileengine.commander.IConvertFile;
import com.fileengine.commander.OnExecuteListener;
import com.fileengine.commander.entity.EngineEntity;
import com.fileengine.commander.entity.IFileEntity;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by linhui on 2017/9/29.
 */
public class Engine2 {
    private IConvertFile convert;
    private EngineEntity engineEntity;
    private OnExecuteListener onExecuteListener;
    private List<FileThread> linkedList = new LinkedList<FileThread>();
    private final Set<IFileEntity> FILES = new LinkedHashSet<IFileEntity>() {
        @Override
        public boolean add(IFileEntity iFileEntity) {
            boolean flag = super.add(iFileEntity);
            if (flag) {
                onExecuteListener.onNext(iFileEntity, size());
            }
            return flag;
        }
    };

    public Engine2() {
    }

    public void prepare(EngineEntity engineEntity, OnExecuteListener onExecuteListener) {
        this.engineEntity = engineEntity;
        this.onExecuteListener = onExecuteListener;
        onExecuteListener.onNext(null,0);
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
        for (int i = 0; i < engineEntity.getSpeed(); i++) {
            FileThread f = new FileThread();
            f.start();
            linkedList.add(f);
        }
    }

    private int i = 0;

    public void startScanner() {


        new Thread(){
            @Override
            public void run() {
                while (true){
                    for(FileThread t : linkedList){
                        synchronized (t.getLock()) {
                            if (!t.isLock()) {
                                continue;
                            }
                        }
                    }
                    break;
                }
                if (linkedList.get(i).addRunnable(new Runnable() {
                    @Override
                    public void run() {
                        findByDir(engineEntity.getFile());
                    }
                }).isLock()) {
                    synchronized (linkedList.get(i).getLock()) {
                        linkedList.get(i).getLock().notify();
                    }
                }

            }
        }.start();


    }

    public void stop() {
        if (linkedList == null) {
            return;
        }
        for (FileThread t : linkedList) {
            t.setStop(true);
            synchronized (t.getLock()) {
                t.getLock().notify();
            }
        }
        FILES.clear();
        linkedList.clear();
    }


    private void findByDir(File file) {
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                if (pathname.isDirectory()) {
                    i = new Random().nextInt(engineEntity.getSpeed());
                    synchronized (linkedList.get(i).getLock()) {
                        if (linkedList.get(i).addRunnable(new Runnable() {
                            @Override
                            public void run() {
                                findByDir(pathname);
                            }
                        }).isLock()) {
                            linkedList.get(i).getLock().notify();
                        }
                    }
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


}
