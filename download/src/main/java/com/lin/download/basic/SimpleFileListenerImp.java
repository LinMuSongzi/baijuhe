package com.lin.download.basic;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lin.download.basic.provide.Factory;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;

import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/7.
 */
public class SimpleFileListenerImp extends FileDownloadListener {
    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i(Factory.TAG, "pending: ");

    }

    @Override
    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
        super.retry(task, ex, retryingTimes, soFarBytes);
        Log.i(Factory.TAG, "retry: ");
    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
        Log.i(Factory.TAG, "connected: ");
    }

    @Override
    protected void blockComplete(BaseDownloadTask task) throws Throwable {
        super.blockComplete(task);
        Log.i(Factory.TAG, "blockComplete: ");
    }

    @Override
    protected void started(BaseDownloadTask task) {
        super.started(task);
        Log.i(Factory.TAG, "started: ");
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i(Factory.TAG, "progress: ");
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        Log.i(Factory.TAG, "completed: ");
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i(Factory.TAG, "paused: ");
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        Log.i(Factory.TAG, "error: ");
        e.printStackTrace();
    }

    @Override
    protected void warn(BaseDownloadTask task) {
        Log.i(Factory.TAG, "warn: ");
    }
}
