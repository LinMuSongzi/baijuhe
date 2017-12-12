package com.lin.download.basic;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.os.Environment;

import com.lin.download.business.model.DownLoadInfo;
import com.lin.download.business.BusinessWrap;
import com.lin.download.business.WorkController;
import com.lin.download.util.DownloadUtil;

import java.io.File;

import y.com.sqlitesdk.framework.util.MD5Util;

/**
 * Created by linhui on 2017/12/11.
 *
 * 尽量所有操作都在此类中调用
 *
 */
public class Entrance {
    public static final String TAG = "DownloadPlan";

    public static void init(Context context){
        WorkController.getInstance().init(context);
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {BusinessWrap.addOperatorRespone(operatorRespone);}

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {BusinessWrap.removeOperatorRespone(operatorRespone);}

    public static void pause(int tableId) {
        WorkController.getInstance().pause(tableId);
    }

    public static void download(int tableId) {
        WorkController.getInstance().download(tableId);}

    public static void delete(int tableId,boolean isdeleteFile) {
        WorkController.getInstance().delete(tableId,isdeleteFile);
    }

    public static void reset(int tableId) {
        WorkController.getInstance().reset(tableId);
    }

    public static void pauseAll(){
        WorkController.getInstance().pauseAll();}

    public static void addTask(DownLoadInfo downLoadTable) {
        WorkController.getInstance().addTask(downLoadTable);}

    public static void notifyAllQueryDownload(ContentObserver c) {BusinessWrap.notifyAllQueryDownload(c);}

    public static void findStutasDownloadList(int code, int stutas) {BusinessWrap.findStutasDownloadList(code, stutas);}

    public static void launchApp(Context context,String packageName, String appPath){BusinessWrap.launchApp(context,packageName,appPath);}

    public static void launchApp(Context context, String appPath){BusinessWrap.launchApp(context,getPackageName(context,appPath),appPath);}

    public static String getPackageName(Context context,String appPath) {return context.getPackageManager().getPackageArchiveInfo(appPath, PackageManager.GET_ACTIVITIES).packageName;}

    public static Plan createSimplePlan(int tableId){
        return PlanImp.getNewInstance(tableId);
    }

    public static void deleteSavePath(String  savePath) {
        WorkController.getInstance().deleteSavePath(savePath);
    }

    public static DownLoadInfo getInfoBySavePath(String savePath){
        return BusinessWrap.getInfoBySavePath(savePath);
    }


    public static DownLoadInfo createInfo(String objectId,String downloadUrl,String savePath,String name){

        DownLoadInfo downLoadTable = new DownLoadInfo();
        downLoadTable.setDownloadUrl(downloadUrl);
        downLoadTable.setSavePath(savePath);
        downLoadTable.setName(name);
        downLoadTable.setObjectId(objectId);
        return downLoadTable;

    }

}
