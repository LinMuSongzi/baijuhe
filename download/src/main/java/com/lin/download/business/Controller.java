package com.lin.download.business;

import android.content.Context;

import com.lin.download.business.model.DownLoadInfo;

/**
 * Created by linhui on 2017/12/11.
 */
public interface Controller {
    void init(Context context);
    void pause(int tableId);
    void download(int tableId);
    void delete(int tableId);
    void reset(int tableId);
    void addTask(DownLoadInfo downLoadTable);
    void pauseAll();
    void deleteSavePath(String savePath);
}
