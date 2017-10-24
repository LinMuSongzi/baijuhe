package com.fileengine.commander.entity;

import android.os.Environment;

import com.fileengine.commander.IEngine;

import java.io.File;

/**
 * Created by linhui on 2017/9/28.
 */
public class EngineEntity {

    int speed = IEngine.DEFAULT_THREAD_COUNT;

    int nextTime = IEngine.DEFAULT_TIME;;

    File file = Environment.getExternalStorageDirectory();

    String[] postfix = new String[]{".jpg", ".jpeg", "png"};

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String[] getPostfix() {
        return postfix;
    }

    public void setPostfix(String[] postfix) {
        this.postfix = postfix;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getNextTime() {
        return nextTime;
    }

    public void setNextTime(int nextTime) {
        this.nextTime = nextTime;
    }
}
