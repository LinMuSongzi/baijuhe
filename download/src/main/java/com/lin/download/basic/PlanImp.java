package com.lin.download.basic;

import android.os.Handler;
import android.os.Looper;

import com.lin.download.business.WorkController;
import com.lin.download.business.model.BaseModel;
import com.lin.download.business.model.DownLoadInfo;
import com.lin.download.business.BusinessWrap;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
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

    private void download2() {
        final int id = downLoadTable.getId();
        baseDownloadTaskId = (baseDownloadTask = FileDownloader.getImpl().
                create(downLoadTable.getDownLoadUrl()).setListener(new SimpleFileListenerImp() {

            @Override
            protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
                BusinessWrap.progress(id, soFarBytes, totalBytes);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);
                BusinessWrap.completed(id);
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
                BusinessWrap.paused(id);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);
                BusinessWrap.error(id);

                if (e instanceof FileDownloadOutOfSpaceException) {

                    //空间不足


                } else if (e instanceof UnknownHostException) {

                    //无网络或者硬件损坏

                }


            }
        }).setPath(downLoadTable.getSavePath()).setSyncCallback(true)).start();
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
    public boolean isRunning() {
        return baseDownloadTask != null && baseDownloadTask.isRunning();
    }

    @Override
    public void delete(boolean isDeleteFile) {
        this.pause();
        BusinessWrap.delete(tableId, downLoadTable != null ? downLoadTable.getSavePath() : "", isDeleteFile);
    }

    @Override
    public void reset() {
        this.pause();
        BusinessWrap.reset(tableId);
        WorkController.getInstance().download(downLoadTable);
    }

    @Override
    public void pause() {
        if (baseDownloadTask != null && baseDownloadTask.isRunning()) {
            baseDownloadTask.pause();
            baseDownloadTask = null;
        } else {
            BusinessWrap.paused(tableId);
        }
    }
}
