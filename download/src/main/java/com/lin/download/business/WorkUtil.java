package com.lin.download.business;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.lin.download.basic.IBasicInfo;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.business.model.DownLoadInfo;

import java.io.File;
import java.util.List;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.interface_model.IModel;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;
import y.com.sqlitesdk.framework.util.StringDdUtil;

/**
 * Created by linhui on 2017/12/9.
 */
class WorkUtil {

    static OperatorRespone findOperatorRespone(int code) {
        synchronized (WorkController.getOperatorRespones()) {
            for (OperatorRespone operatorRespone : WorkController.getOperatorRespones()) {
                if (operatorRespone.getCode() == code) {
                    return operatorRespone;
                }
            }
            return null;
        }
    }

    static void addOperatorRespone(OperatorRespone operatorRespone) {
        WorkController.downLoadViewController.addOperatorRespone(operatorRespone);
    }

    static void removeOperatorRespone(OperatorRespone operatorRespone) {
        WorkController.downLoadViewController.removeOperatorRespone(operatorRespone);
    }

    /**
     * 添加一个下载任务任务
     *
     * @param downLoadTable
     */
    static void addDownloadTask(final DownLoadInfo downLoadTable) {

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
    static void progress(int id, final long soFarBytes, final long totalBytes) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d,current = %d,toTal = %d where id = %d",
                        DownLoadInfo.TB_NAME, DownLoadInfo.DOING_STATUS, soFarBytes, totalBytes, i);
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
    static void completed(int id) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d where id = %d",
                        DownLoadInfo.TB_NAME, DownLoadInfo.COMPLETED_STATUS, i);
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
    static void paused(int id) {
        final int i = id;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d where id = %d",
                        DownLoadInfo.TB_NAME, DownLoadInfo.PAUSE_STATUS, i);


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
    static void error(int id) {

        final int i = id;

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {

                String sql = String.format(
                        "update %s set status = %d where id = %d",
                        DownLoadInfo.TB_NAME, DownLoadInfo.ERROR_STATUS, i);

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
     * 唤醒contentprovide
     *
     * @param c
     */
    static void notifyAllQueryDownload(ContentObserver c) {
        WorkController.getContext().getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, c);
    }

    /**
     * 找到对应状态的下载文件
     *
     * @param code
     * @param stutas
     */
    static void findStutasDownloadList(final int code, final int stutas) {
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                List<DownLoadInfo> loadTables = BusinessUtil.reflectCursor(sqLiteDatabase.rawQuery("select * from " +
                        DownLoadInfo.TB_NAME + " where status = " + stutas, null), DownLoadInfo.class);
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
    static void useOperatorRespone(int code, Object object) {
        final OperatorRespone operatorRespone
                = findOperatorRespone(code);
        if (operatorRespone != null) {
            operatorRespone.success(object);
        }
    }

    /**
     * 删除某个id，刷新页面
     *
     * @param tableId
     */
    static void resolverDeleteInfoById(int tableId) {
        if (tableId > 0) {
            WorkController.getContext().getContentResolver().delete(
                    DownLoadProvider.CONTENT_DELETE_URI, "id = ?",
                    new String[]{String.valueOf(tableId)});
        }
    }

    /**
     * 删除
     *
     * @param tableId
     * @param savePath
     */
    static void delete(final int tableId, final String savePath) {


        if (tableId > 0 && !StringDdUtil.isNull(savePath)) {
            deleteSavePath(savePath);
            resolverDeleteInfoById(tableId);
        } else if (tableId > 0 && StringDdUtil.isNull(savePath)) {
            IBasicInfo info = WorkUtil.getInfoById(tableId);
            deleteSavePath(info == null ? "" : info.getSavePath());
            resolverDeleteInfoById(tableId);
        } else if (tableId < 0 && !StringDdUtil.isNull(savePath)) {
            deleteSavePath(savePath);
            IModel info = WorkUtil.getInfoBySavePath(savePath);
            if (info != null && info.getId() > 0) {
                resolverDeleteInfoById(info.getId());
            }
        }

    }

    /**
     * 根据存储路径获取单行消息
     *
     * @param savePath
     * @return
     */
    static DownLoadInfo getInfoBySavePath(final String savePath) {
        final DownLoadInfo[] downLoadInfo = {null};
        Access.runCustomThread(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                downLoadInfo[0] = BusinessUtil.reflectCursorOne(
                        sqLiteDatabase.query(
                                DownLoadInfo.TB_NAME, null, "save_path = ?",
                                new String[]{savePath}, null, null, null),
                        DownLoadInfo.class,
                        true);
            }

            @Override
            public void onExternalError() {

            }
        });
        return downLoadInfo[0];
    }

    /**
     * 安装或者启动
     *
     * @param context
     * @param packageName
     * @param appPath
     */
    static void launchApp(Context context, String packageName, String appPath) {

        // 启动目标应用
        if (new File("/data/data/" + packageName).exists()) {
            // 获取目标应用安装包的Intent
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(
                    packageName);
            context.startActivity(intent);
        }
        // 安装目标应用
        else {
            Intent intent = new Intent();
            // 设置目标应用安装包路径
            intent.setDataAndType(Uri.fromFile(new File(appPath)),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        }

    }


    static DownLoadInfo getInfoById(final int tableId) {
        final DownLoadInfo[] downLoadInfo = {null};
        Access.runCustomThread(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                downLoadInfo[0] = Business.getInstances().queryById(sqLiteDatabase, tableId, DownLoadInfo.class);
            }

            @Override
            public void onExternalError() {

            }
        });
        return downLoadInfo[0];
    }

    /**
     * 重新下载
     *
     * @param tableid
     */
    static void reset(final int tableid) {
        IBasicInfo downLoadInfo = WorkUtil.getInfoById(tableid);

        if (downLoadInfo != null) {
            deleteSavePath(downLoadInfo.getSavePath());
        }
    }

    /**
     * 删除源文件
     *
     * @param savePath
     */
    static void deleteSavePath(String savePath) {

        if (!StringDdUtil.isNull(savePath)) {
            if (!StringDdUtil.isNull(savePath)) {
                File f = new File(savePath);
                try {
                    if (!f.delete()) {
                        f = new File(savePath + ".temp");
                        f.delete();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
