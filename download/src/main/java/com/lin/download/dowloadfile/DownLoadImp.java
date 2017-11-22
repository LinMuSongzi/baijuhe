package com.lin.download.dowloadfile;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.util.List;

/**
 * Created by linhui on 2017/11/21.
 */
final class DownLoadImp implements DownLoadBase,Initialization {


    private Context context;

    @Override
    public void startAll() {

    }

    @Override
    public void startDownLoadFile(LoadBean loadBean, Object tag) {

    }

    @Override
    public void pause(Object tag) {

    }

    @Override
    public void pause(String url) {

    }

    @Override
    public void pauseAll() {

    }

    @Override
    public void againDownLoad(LoadBean loadBean, Object tag) {

    }

    @Override
    public void quitDownLoad(LoadBean loadBean, boolean isDeleteFile) {

    }

    @Override
    public void delteFile(File file) {

    }

    @Override
    public List<LoadBean> getAllDownLoad() {
        return null;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }
}
