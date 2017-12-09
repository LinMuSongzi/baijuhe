package com.lin.download.business;

import android.support.v7.app.AlertDialog;

import com.lin.download.basic.provide.table.DownLoadTable;
import com.liulishuo.filedownloader.BaseDownloadTask;

/**
 * Created by linhui on 2017/12/9.
 */
public abstract class DownloadPiece implements Runnable {


    private BaseDownloadTask baseDownloadTask;

    DownloadPiece(){}


    @Override
    public void run() {
        download();
    }

    protected void download(){

    }



    protected final void createTask(){

    }

}
