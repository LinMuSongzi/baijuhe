package com.lin.download.basic;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    }

    @Override
    protected void warn(BaseDownloadTask task) {
        Log.i(Factory.TAG, "warn: ");
    }
}
