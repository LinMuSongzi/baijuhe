package com.lin.download.basic;

import com.lin.download.business.model.DownLoadTable;
import com.lin.download.business.BusinessWrap;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by linhui on 2017/12/7.
 */
public class PlanImp implements Plan {


    private DownLoadTable downLoadTable;
    private BaseDownloadTask baseDownloadTask;
    private int baseDownloadTaskId;
    private int tableId;

    public PlanImp(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public void download() {
        if (baseDownloadTask == null) {
            DownLoadTable d1 = new DownLoadTable();
            d1.setId(tableId);
            downLoadTable = BusinessWrap.queryById(d1);
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
        if (baseDownloadTask != null) {
            if (baseDownloadTask.isRunning()) {
                baseDownloadTask.pause();
            }
            while (!baseDownloadTask.isRunning()) {
                break;
            }
        }
        BusinessWrap.delete(tableId, downLoadTable != null ? downLoadTable.getSavePath() : "");
    }

    @Override
    public void reset() {
        if (baseDownloadTask!=null && baseDownloadTask.isRunning()) {
            baseDownloadTask.pause();
        }
        BusinessWrap.delete(-1, downLoadTable != null ? downLoadTable.getSavePath() : "");
        download();
    }

    @Override
    public void pause() {
        if (baseDownloadTask!=null &&baseDownloadTask.isRunning()) {
            baseDownloadTask.pause();
        }
    }
}
