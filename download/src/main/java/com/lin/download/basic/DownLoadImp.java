package com.lin.download.basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.DocumentsProvider;
import android.util.Log;

import com.lin.download.MyApp;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.basic.provide.SupportProvide;
import com.lin.download.basic.provide.table.DownLoadTable;
import com.lin.download.util.DownloadUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.DocumentType;

import java.util.Queue;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.entity.respone.QuerySingleRespone;
import y.com.sqlitesdk.framework.interface_model.IModel;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadImp implements DownLoadExpress ,Runnable{

    int id;
    private final String TAG = "DownLoadImp";

    public DownLoadImp(int id) {
        this.id = id;
    }

    private DownLoadTable downLoadTable;

    public void download() {
        downLoadTable = new DownLoadTable();
        downLoadTable.setId(id);
        Access.runCustomThread(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                downLoadTable = Business.getInstances().queryById(sqLiteDatabase,downLoadTable);
            }

            @Override
            public void onExternalError() {

            }
        });
        if(downLoadTable.getId() > 0){
            download2();
        }

    }

    private BaseDownloadTask download2() {
        BaseDownloadTask baseDownloadTask;
        final int i = (baseDownloadTask = FileDownloader.getImpl().
                create(downLoadTable.getDownLoadUrl()).setListener(new SimpleFileListenerImp() {

            @Override
            protected void pending(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                super.pending(task, soFarBytes, totalBytes);

//                Access.run(new Execute() {
//                    @Override
//                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
//
//                        String sql = String.format(
//                                "update %s set stutas = %d,current = %d,toTal = %d where id = %d",
//                                DownLoadTable.TB_NAME,DownLoadTable.DOING, soFarBytes, totalBytes, downLoadTable.getId());
//                        Log.i(TAG, "pending: "+sql);
//                        sqLiteDatabase.execSQL(sql);
//                        MyApp.app.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, null);
//                    }
//
//                    @Override
//                    public void onExternalError() {
//
//                    }
//                });
            }

            @Override
            protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
                Access.run(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                        String sql = String.format(
                                "update %s set stutas = %d,current = %d,toTal = %d where id = %d",
                                DownLoadTable.TB_NAME,DownLoadTable.DOING, soFarBytes,totalBytes, downLoadTable.getId());

                        Log.i(TAG, "progress: "+sql);


                        sqLiteDatabase.execSQL(sql);
                        MyApp.app.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, null);
                    }

                    @Override
                    public void onExternalError() {

                    }
                });
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);


                Access.run(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                        String sql =String.format(
                                "update %s set stutas = %d where id = %d",
                                DownLoadTable.TB_NAME,DownLoadTable.COMPLETED, downLoadTable.getId());

                        Log.i(TAG, "completed: "+sql);

                        sqLiteDatabase.execSQL(sql);
                        MyApp.app.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, null);
                    }

                    @Override
                    public void onExternalError() {

                    }
                });

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
                Access.run(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                        String sql = String.format(
                                "update %s set stutas = %d where id = %d",
                                DownLoadTable.TB_NAME,DownLoadTable.PAUSE, downLoadTable.getId());


                        Log.i(TAG, "paused: "+sql);

                        sqLiteDatabase.execSQL(sql);
                        MyApp.app.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, null);
                    }

                    @Override
                    public void onExternalError() {

                    }
                });
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);

                Access.run(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {

                        String sql = String.format(
                                "update %s set stutas = %d where id = %d",
                                DownLoadTable.TB_NAME,DownLoadTable.ERROR, downLoadTable.getId());

                        Log.i(TAG, "paused: "+sql);

                        sqLiteDatabase.execSQL(sql);
                        MyApp.app.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, null);
                    }

                    @Override
                    public void onExternalError() {

                    }
                });
            }
        }).setPath(downLoadTable.getSavePath()).setSyncCallback(true).setAutoRetryTimes(AUTO_RETRY_TIMES)).start();
//        downLoadTable.setDownLoadId(String.valueOf(i));
        return baseDownloadTask;

    }

    public void run() {
        download();
    }
}
