package com.lin.download.dowloadfile;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by linhui on 2017/11/21.
 */
public class FeimoDownLoadManager implements DownLoadBase {


    static DownLoadBase downLoadBase;

    static {
        downLoadBase = new DownLoadImp();
    }

    public static DownLoadBase getInstances() {
        return downLoadBase;
    }

    public static final void init(Application application) {
        if (downLoadBase instanceof Initialization) {
            ((Initialization) downLoadBase).init(application);
        }
    }

    @Override
    public void startAll() {
        downLoadBase.startAll();
    }

    @Override
    public void startDownLoadFile(LoadBean loadBean, Object tag) {
        downLoadBase.startDownLoadFile(loadBean, tag);
    }

    @Override
    public void pause(Object tag) {
        downLoadBase.pause(tag);
    }

    @Override
    public void pause(String url) {
        downLoadBase.pause(url);
    }

    @Override
    public void pauseAll() {
        downLoadBase.pauseAll();
    }

    @Override
    public void againDownLoad(LoadBean loadBean, Object tag) {
        downLoadBase.againDownLoad(loadBean, tag);
    }

    @Override
    public void quitDownLoad(LoadBean loadBean, boolean isDeleteFile) {
        downLoadBase.quitDownLoad(loadBean, isDeleteFile);
    }

    @Override
    public void delteFile(File file) {
        downLoadBase.delteFile(file);
    }

    @Override
    public List<LoadBean> getAllDownLoad() {
        return downLoadBase.getAllDownLoad();
    }
}
