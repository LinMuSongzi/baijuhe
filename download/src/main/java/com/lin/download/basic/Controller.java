package com.lin.download.basic;

import android.content.Context;

import com.lin.download.business.model.DownLoadInfo;

/**
 * Created by linhui on 2017/12/11.
 */
public interface Controller {

    int MAX_DOWNLOAD_COUNT = 2;

    void init(Context context);
    void pause(int tableId);
    void download(int tableId);
    void delete(int tableId,boolean isDeleteFile);
    void reset(int tableId);
    void addTask(DownLoadInfo downLoadTable);
    void pauseAll();
    void deleteSavePath(String savePath);
}
