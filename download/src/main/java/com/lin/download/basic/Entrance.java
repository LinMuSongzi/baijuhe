package com.lin.download.basic;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;

import com.lin.download.business.model.DownLoadInfo;
import com.lin.download.business.BusinessWrap;
import com.lin.download.business.WorkController;
import com.lin.download.business.OperatorRespone;

import y.com.sqlitesdk.framework.business.Business;

/**
 * Created by linhui on 2017/12/11.
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

    public static void delete(int tableId) {
        WorkController.getInstance().delete(tableId);
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


}
