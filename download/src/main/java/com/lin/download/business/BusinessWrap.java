package com.lin.download.business;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;

import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.business.model.DownLoadTable;

import java.io.File;

import y.com.sqlitesdk.framework.util.StringDdUtil;

/**
 * Created by linhui on 2017/12/11.
 */
public class BusinessWrap {

    /**
     * 添加一个下载任务任务
     *
     * @param downLoadTable
     */
    public static void addDownloadTask(DownLoadTable downLoadTable) {
        DownloadBusinessUtil.addDownloadTask(downLoadTable);
    }

    /**
     * 暂停
     *
     * @param tableId
     * @param soFarBytes
     * @param totalBytes
     */
    public static void progress(int tableId, final long soFarBytes, final long totalBytes) {
        DownloadBusinessUtil.progress(tableId, soFarBytes, totalBytes);
    }

    /**
     * 完成
     *
     * @param tableId
     */
    public static void completed(int tableId) {
        DownloadBusinessUtil.completed(tableId);
    }

    /**
     * 暂停
     *
     * @param tableId
     */
    public static void paused(int tableId) {
        DownloadBusinessUtil.paused(tableId);
    }

    /**
     * 错误
     * 有可能没有存储权限，或者磁盘不够，或者网络错误
     *
     * @param tableId
     */
    public static void error(int tableId) {
        DownloadBusinessUtil.error(tableId);
    }

    /**
     * id查找
     *
     * @param d
     * @return
     */
    public static DownLoadTable queryById(final DownLoadTable d) {
        return DownloadBusinessUtil.queryById(d);
    }

    /**
     * 唤醒contentprovide
     *
     * @param c
     */
    public static void notifyAllQueryDownload(ContentObserver c) {
        DownloadBusinessUtil.notifyAllQueryDownload(c);
    }

    /**
     * 找到对应状态的下载文件
     *
     * @param code
     * @param stutas
     */
    public static void findStutasDownloadList(int code, int stutas) {
        DownloadBusinessUtil.findStutasDownloadList(code, stutas);
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {
        DownloadBusinessUtil.addOperatorRespone(operatorRespone);
    }

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {
        DownloadBusinessUtil.removeOperatorRespone(operatorRespone);
    }

    public static void delete(int tableId, String savePath) {

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
        if(tableId > 0) {
            DownLoadViewController.getContext().getContentResolver().delete(
                    DownLoadProvider.CONTENT_DELETE_URI, "id = " + tableId, null);
        }

    }

    public static void launchApp(Context context,String packageName, String appPath) {
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

}
