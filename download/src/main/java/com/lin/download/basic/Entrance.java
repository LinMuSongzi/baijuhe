package com.lin.download.basic;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;

import com.lin.download.business.model.DownLoadTable;
import com.lin.download.business.BusinessWrap;
import com.lin.download.business.DownLoadViewController;
import com.lin.download.business.OperatorRespone;

/**
 * Created by linhui on 2017/12/11.
 */
public class Entrance {
    public static final String TAG = "DownloadPlan";

    public static void init(Context context){
        DownLoadViewController.getInstance().init(context);
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {BusinessWrap.addOperatorRespone(operatorRespone);}

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {BusinessWrap.removeOperatorRespone(operatorRespone);}

    public static void pause(int tableId) {
        DownLoadViewController.getInstance().pause(tableId);
    }

    public static void download(int tableId) {DownLoadViewController.getInstance().download(tableId);}

    public static void delete(int tableId) {
        DownLoadViewController.getInstance().delete(tableId);
    }

    public static void reset(int tableId) {
        DownLoadViewController.getInstance().reset(tableId);
    }

    public static void pauseAll(){DownLoadViewController.getInstance().pauseAll();}

    public static void addTask(DownLoadTable downLoadTable) {DownLoadViewController.getInstance().addTask(downLoadTable);}

    public static void notifyAllQueryDownload(ContentObserver c) {BusinessWrap.notifyAllQueryDownload(c);}

    public static void findStutasDownloadList(int code, int stutas) {BusinessWrap.findStutasDownloadList(code, stutas);}

    public static void launchApp(Context context,String packageName, String appPath){BusinessWrap.launchApp(context,packageName,appPath);}

    public static void launchApp(Context context, String appPath){BusinessWrap.launchApp(context,getPackageName(context,appPath),appPath);}

    public static String getPackageName(Context context,String appPath) {return context.getPackageManager().getPackageArchiveInfo(appPath, PackageManager.GET_ACTIVITIES).packageName;}

}
