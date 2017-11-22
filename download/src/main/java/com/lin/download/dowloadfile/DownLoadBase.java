package com.lin.download.dowloadfile;

import android.app.Application;

import java.io.File;
import java.lang.annotation.Target;
import java.util.List;

/**
 * Created by linhui on 2017/11/21.
 */
public interface DownLoadBase {


    void startAll();

    void startDownLoadFile(LoadBean loadBean, Object tag);

    void pause(Object tag);

    void pause(String url);

    void pauseAll();

    void againDownLoad(LoadBean loadBean, Object tag);

    void quitDownLoad(LoadBean loadBean,boolean isDeleteFile);

    void delteFile(File file);

    List<LoadBean> getAllDownLoad();

}
