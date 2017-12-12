package com.lin.download.basic;

import com.lin.download.business.WorkController;
import com.lin.download.business.model.DownLoadInfo;
import com.lin.download.business.BusinessWrap;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.exception.FileDownloadOutOfSpaceException;

import java.net.UnknownHostException;

import y.com.sqlitesdk.framework.business.Business;

/**
 * Created by linhui on 2017/12/7.
 */
public class PlanImp implements Plan {


    private DownLoadInfo downLoadTable;
    private BaseDownloadTask baseDownloadTask;
    private int baseDownloadTaskId;
    private int tableId;
    private PlanImp(int tableId) {
        this.tableId = tableId;
    }
    static Plan getNewInstance(int tableId) {
        return new PlanImp(tableId);
    }
    @Override
    public void download() {
        if (baseDownloadTask == null) {
            downLoadTable = BusinessWrap.getInfoById(tableId);
            if (downLoadTable.getId() > 0) {
                download2();
            }
        } else {
            if (!baseDownloadTask.isRunning()) {
                if (baseDownloadTask.isUsing()) {
                    baseDownloadTask.reuse();
                }
                baseDownloadTask.start();
            }
        }
    }
    private BaseDownloadTask download2() {
        baseDownloadTaskId = (baseDownloadTask = FileDownloader.getImpl().
                create(downLoadTable.getDownLoadUrl()).setListener(new SimpleFileListenerImp() {

            @Override
            protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
                BusinessWrap.progress(downLoadTable.getId(), soFarBytes, totalBytes);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);
                BusinessWrap.completed(downLoadTable.getId());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
                BusinessWrap.paused(downLoadTable.getId());
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);
                BusinessWrap.error(downLoadTable.getId());

                if (e instanceof FileDownloadOutOfSpaceException) {

                    //空间不足


                }else if(e instanceof UnknownHostException){

                    //无网络或者硬件损坏

                }


            }
        }).setPath(downLoadTable.getSavePath()).setSyncCallback(true).setAutoRetryTimes(AUTO_RETRY_TIMES)).start();
        return baseDownloadTask;

    }
    @Override
    public void run() {
        download();
    }
    @Override
    public int getModelId() {
        return tableId;
    }
    @Override
    public void delete() {
        this.pause();
        BusinessWrap.delete(tableId, downLoadTable != null ? downLoadTable.getSavePath() : "");
    }
    @Override
    public void reset() {
        this.pause();
        BusinessWrap.reset(tableId);
        WorkController.getInstance().download(tableId);
    }
    @Override
    public void pause() {
        if (baseDownloadTask != null && baseDownloadTask.isRunning()) {
            baseDownloadTask.pause();
        }
    }
}
