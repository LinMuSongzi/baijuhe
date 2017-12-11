package com.lin.download.business;

import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;

import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.business.model.DownLoadTable;

import java.util.List;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/9.
 */
class DownloadBusinessUtil {

    public static OperatorRespone findOperatorRespone(int code) {
        synchronized (DownLoadViewController.getOperatorRespones()) {
            for (OperatorRespone operatorRespone : DownLoadViewController.getOperatorRespones()) {
                if (operatorRespone.getCode() == code) {
                    return operatorRespone;
                }
            }
            return null;
        }
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {
        DownLoadViewController.downLoadViewController.addOperatorRespone(operatorRespone);
    }

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {
        DownLoadViewController.downLoadViewController.removeOperatorRespone(operatorRespone);
    }

    /**
     * 添加一个下载任务任务
     *
     * @param downLoadTable
     */
    public static void addDownloadTask(final DownLoadTable downLoadTable) {
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                long leng = Business.getInstances().insert(sqLiteDatabase, downLoadTable);
                if (leng > 0) {
                    notifyAllQueryDownload(null);
                }
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    /**
     * 暂停
     *
     * @param id
     * @param soFarBytes
     * @param totalBytes
     */
    public static void progress(int id, final long soFarBytes, final long totalBytes) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d,current = %d,toTal = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.DOING_STATUS, soFarBytes, totalBytes, i);
                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });
    }

    /**
     * 完成
     *
     * @param id
     */
    public static void completed(int id) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.COMPLETED_STATUS, i);
//                Log.i(TAG, "completed: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });
    }

    /**
     * 暂停
     *
     * @param id
     */
    public static void paused(int id) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.PAUSE_STATUS, i);


//                Log.i(TAG, "paused: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    /**
     * 错误
     * 有可能没有存储权限，或者磁盘不够，或者网络错误
     *
     * @param id
     */
    public static void error(int id) {

        final int i = id;

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {

                String sql = String.format(
                        "update %s set status = %d where id = %d",
                        DownLoadTable.TB_NAME, DownLoadTable.ERROR_STATUS, i);

//                Log.i(TAG, "paused: "+sql);

                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    /**
     * id查找
     *
     * @param d
     * @return
     */
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

    /**
     * 唤醒contentprovide
     *
     * @param c
     */
    public static void notifyAllQueryDownload(ContentObserver c) {
        DownLoadViewController.getContext().getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, c);
    }

    /**
     * 找到对应状态的下载文件
     *
     * @param code
     * @param stutas
     */
    public static void findStutasDownloadList(final int code, final int stutas) {
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

    /**
     * 使用操作回调
     *
     * @param code   标识
     * @param object 回调对象
     */
    private static void useOperatorRespone(int code, Object object) {
        final OperatorRespone operatorRespone
                = findOperatorRespone(code);
        if (operatorRespone != null) {
            operatorRespone.success(object);
        }
    }

}
