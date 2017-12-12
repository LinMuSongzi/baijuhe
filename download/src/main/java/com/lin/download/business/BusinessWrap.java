package com.lin.download.business;

import android.content.Context;
import android.database.ContentObserver;

import com.lin.download.business.model.DownLoadInfo;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by linhui on 2017/12/11.
 */
public class BusinessWrap {

    /**
     * 添加一个下载任务任务
     *
     * @param downLoadTable
     */
    public static void addDownloadTask(DownLoadInfo downLoadTable) {
        WorkUtil.addDownloadTask(downLoadTable);
    }

    /**
     * 暂停
     *
     * @param tableId
     * @param soFarBytes
     * @param totalBytes
     */
    public static void progress(int tableId, final long soFarBytes, final long totalBytes) {
        WorkUtil.progress(tableId, soFarBytes, totalBytes);
    }

    /**
     * 完成
     *
     * @param tableId
     */
    public static void completed(int tableId) {
        WorkUtil.completed(tableId);
    }

    /**
     * 暂停
     *
     * @param tableId
     */
    public static void paused(int tableId) {
        WorkUtil.paused(tableId);
    }

    /**
     * 错误
     * 有可能没有存储权限，或者磁盘不够，或者网络错误
     *
     * @param tableId
     */
    public static void error(int tableId) {
        WorkUtil.error(tableId);
    }

    /**
     * id查找
     *
     * @param d
     * @return
     */
    public static DownLoadInfo getInfoById(int tableId) {
        return WorkUtil.getInfoById(tableId);
    }

    /**
     * 唤醒contentprovide
     *
     * @param c
     */
    public static void notifyAllQueryDownload(ContentObserver c) {
        WorkUtil.notifyAllQueryDownload(c);
    }

    /**
     * 找到对应状态的下载文件
     *
     * @param code
     * @param stutas
     */
    public static void findStutasDownloadList(int code, int stutas) {
        WorkUtil.findStutasDownloadList(code, stutas);
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {
        WorkUtil.addOperatorRespone(operatorRespone);
    }

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {
        WorkUtil.removeOperatorRespone(operatorRespone);
    }

    public static void delete(int tableId, String savePath) {
        WorkUtil.delete(tableId, savePath);
    }

    public static void launchApp(Context context, String packageName, String appPath) {
        WorkUtil.launchApp(context,packageName,appPath);
    }

    public static void reset(int table) {
        WorkUtil.reset(table);
    }

    /**
     * 只删除文件
     * @param savePath
     */
    public static void deleteSavePath(String  savePath) {
        WorkUtil.deleteSavePath(savePath);
    }

    public static DownLoadInfo getInfoBySavePath(String savePath){
        return WorkUtil.getInfoBySavePath(savePath);
    }

    public static void pauseAll(){
        FileDownloader.getImpl().pauseAll();
    }
}
