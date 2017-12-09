package com.lin.download.business;

import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lin.download.MyApp;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.basic.provide.table.DownLoadTable;

import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/9.
 */
public class DownloadBusiness {

//    public static void


    public static void progress(final int id,final long soFarBytes,final long totalBytes){

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set stutas = %d,current = %d,toTal = %d where id = %d",
                        DownLoadTable.TB_NAME,DownLoadTable.DOING, soFarBytes,totalBytes, id);
                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });
    }

    public static void completed(final int id){

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql =String.format(
                        "update %s set stutas = %d where id = %d",
                        DownLoadTable.TB_NAME,DownLoadTable.COMPLETED, id);
//                Log.i(TAG, "completed: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    public static void paused(final int id){

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set stutas = %d where id = %d",
                        DownLoadTable.TB_NAME,DownLoadTable.PAUSE, id);


//                Log.i(TAG, "paused: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    public static void error(final int id){

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {

                String sql = String.format(
                        "update %s set stutas = %d where id = %d",
                        DownLoadTable.TB_NAME,DownLoadTable.ERROR, id);

//                Log.i(TAG, "paused: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    public static void notifyAllQueryDownload(ContentObserver c) {
        MyApp.app.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, c);
    }


}
