package com.lin.download.business;

import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lin.download.MyApp;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.basic.provide.table.DownLoadTable;
import com.lin.download.business.model.Download2UrlTable;
import com.lin.download.business.model.UrlTable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/9.
 */
public class DownloadBusiness {


    private Set<OperatorRespone> operatorRespones = new HashSet<>();
    private AtomicBoolean isLeave = new AtomicBoolean(false);

    public void leave() {
        synchronized (this) {
            isLeave.set(true);
            operatorRespones.clear();
            operatorRespones = null;
        }
    }

    private void useOperatorRespone(int code, Object object) {
        if (!isLeave.get()) {
            final OperatorRespone operatorRespone
                    = findOperatorRespone(code);
            if (operatorRespone != null) {
                operatorRespone.success(object);
            }
        }
    }

    private OperatorRespone findOperatorRespone(int code) {
        synchronized (this) {
            for (OperatorRespone operatorRespone : operatorRespones) {
                if (operatorRespone.getCode() == code) {
                    return operatorRespone;
                }
            }
            return null;
        }
    }

    public void addOperatorRespone(OperatorRespone operatorRespone) {
        if (operatorRespone != null) {
            synchronized (this) {
                operatorRespones.add(operatorRespone);
            }
        }
    }

    public void removeOperatorRespone(OperatorRespone operatorRespone) {
        if (operatorRespone != null) {
            synchronized (this) {
                operatorRespones.remove(operatorRespone);
            }
        }
    }


    /**
     * 添加一个下载任务任务
     *
     * @param downLoadTable
     */
    public static void addDownloadTask(final DownLoadTable downLoadTable) {
//        final int c = code;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                long leng = Business.getInstances().insert(sqLiteDatabase, downLoadTable);
                if (leng > 0) {
                    notifyAllQueryDownload(null);
//                    useOperatorRespone(c, leng);
                }
            }

            @Override
            public void onExternalError() {

            }
        });

    }


    public static void progress(int id, final long soFarBytes, final long totalBytes) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set stutas = %d,current = %d,toTal = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.DOING, soFarBytes, totalBytes, i);
                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });
    }

    public static void completed(int id) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set stutas = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.COMPLETED, i);
//                Log.i(TAG, "completed: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    public static void paused(int id) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set stutas = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.PAUSE, i);


//                Log.i(TAG, "paused: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    public static void error(int id) {

        final int i = id;

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {

                String sql = String.format(
                        "update %s set stutas = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.ERROR, i);

//                Log.i(TAG, "paused: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }


    public static DownLoadTable queryById(final DownLoadTable d) {
        final DownLoadTable[] downLoadTable = {null};
        Access.runCustomThread(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                downLoadTable[0] = Business.getInstances().queryById(sqLiteDatabase, d);
            }

            @Override
            public void onExternalError() {

            }
        });
        return downLoadTable[0];
    }


    public static void notifyAllQueryDownload(ContentObserver c) {
        MyApp.app.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, c);
    }


    public void findStutasDownloadList(final int code, final int stutas) {
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                List<DownLoadTable> loadTables = BusinessUtil.reflectCursor(sqLiteDatabase.rawQuery("select * from " +
                        DownLoadTable.TB_NAME + " where stutas = " + stutas, null), DownLoadTable.class);
                useOperatorRespone(code, loadTables);
            }

            @Override
            public void onExternalError() {

            }
        });


    }

}
