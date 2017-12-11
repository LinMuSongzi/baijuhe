package com.lin.download.basic;

import com.lin.download.MyApp;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.basic.provide.table.DownLoadTable;
import com.lin.download.business.DownloadBusiness;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadExpressPlan implements Plan {


    private DownLoadTable downLoadTable;
    private BaseDownloadTask baseDownloadTask;
    private int baseDownloadTaskId;
    private int id;

    public DownLoadExpressPlan(int id) {
        this.id = id;
    }
    @Override
    public void download() {
        if (baseDownloadTask == null) {
            DownLoadTable d1 = new DownLoadTable();
            d1.setId(id);
            downLoadTable = DownloadBusiness.queryById(d1);
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
            protected void pending(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                super.pending(task, soFarBytes, totalBytes);

            }

            @Override
            protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
                DownloadBusiness.progress(downLoadTable.getId(), soFarBytes, totalBytes);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);
                DownloadBusiness.completed(downLoadTable.getId());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
                DownloadBusiness.paused(downLoadTable.getId());
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);
                DownloadBusiness.error(downLoadTable.getId());
            }
        }).setPath(downLoadTable.getSavePath()).setSyncCallback(true).setAutoRetryTimes(AUTO_RETRY_TIMES)).start();
//        downLoadTable.setDownLoadId(String.valueOf(i));
        return baseDownloadTask;

    }
    @Override
    public void run() {
        download();
    }
    @Override
    public int getModelId() {
        return id;
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
        File f = new File(downLoadTable.getSavePath());
        try {
            f.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        MyApp.app.getContentResolver().delete(
                DownLoadProvider.CONTENT_DELETE_URI, "id = " + id, null);
    }
    @Override
    public void reset() {
    }
    @Override
    public void pause() {
        if (baseDownloadTask.isRunning()) {
            baseDownloadTask.pause();
        }
    }
}
