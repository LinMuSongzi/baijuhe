package com.lin.download.business;

import android.content.Context;
import android.database.ContentObserver;

import com.lin.download.basic.OperatorRespone;
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

    public static void notifyStatus(){
        WorkUtil.notifyStatus();
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

    public static void waitting(int id){WorkUtil.waitting(id);}

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

    /**
     * 删除下载文件与数据库行，不建议直接使用
     *
     * @param tableId
     * @param savePath
     */
    public static void delete(int tableId, String savePath,boolean isDeleteFile) {
        WorkUtil.delete(tableId, savePath,isDeleteFile);
    }

    public static void launchApp(Context context, String packageName, String appPath) {
        WorkUtil.launchApp(context, packageName, appPath);
    }

    /**
     * 重新下载删除源文件，不建议直接使用
     *
     * @param table
     */
    public static void reset(int table) {
        WorkUtil.reset(table);
    }

    /**
     * 只删除文件
     *
     * @param savePath
     */
    public static void deleteSavePath(String savePath) {
        WorkUtil.deleteSavePath(savePath);
    }

    /**
     * 根据path获取一行数据看信息
     *
     * @param savePath
     * @return
     */
    public static DownLoadInfo getInfoBySavePath(String savePath) {
        return WorkUtil.getInfoBySavePath(savePath);
    }

    public static void pauseAll() {
        FileDownloader.getImpl().pauseAll();
    }
}
