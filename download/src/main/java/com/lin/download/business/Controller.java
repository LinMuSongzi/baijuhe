package com.lin.download.business;

import com.lin.download.basic.provide.table.DownLoadTable;

/**
 * Created by linhui on 2017/12/11.
 */
public interface Controller {

    void pause(int tableId);
    void download(int tableId);
    void delete(int tableId);
    void reset(int tableId);
    void addTask(DownLoadTable downLoadTable);
}
