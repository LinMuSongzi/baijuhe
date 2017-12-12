package com.lin.download.business;

import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.lin.download.basic.Controller;
import com.lin.download.basic.Entrance;
import com.lin.download.basic.IBasicInfo;
import com.lin.download.basic.OperatorRespone;
import com.lin.download.basic.Plan;
import com.lin.download.basic.Regulation;
import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.business.model.DownLoadInfo;
import com.lin.download.util.DownloadUtil;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.interface_model.IModel;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/11.
 */
public class WorkController implements Controller, Operator {

    static WorkController downLoadViewController;

    public static Controller getInstance() {
        return downLoadViewController;
    }

    static {
        downLoadViewController = new WorkController();
    }

    private Context context;
    private Handler handler;

    private ContentObserver contentObserver;

    private List<DownLoadInfo> loadInfos = new ArrayList<>();

    private int thisDownLoad;

    private WorkController() {
        HandlerThread handlerThread = new HandlerThread("DownLoadViewController");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());

    }


    Handler getHandler() {
        return handler;
    }


    ContentObserver getContentObserver() {
        return contentObserver;
    }

    /**
     * 操作回调
     */
    private final Set<OperatorRespone> operatorRespones = new HashSet<>();

    /**
     * 下载计划
     */
    private final Set<Plan> PLANS = new HashSet<Plan>() {
        @Override
        public boolean add(Plan o) {
            synchronized (WorkController.this) {
                return super.add(o);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized (WorkController.this) {
                return super.remove(o);
            }
        }
    };

    /**
     * 根据数据库表格id得到下载计划
     *
     * @param modelId
     * @return
     */
    private Plan userDownLoadImp(int modelId) {
        for (Plan plan : PLANS) {
            if (plan.getModelId() == modelId) {
                return plan;
            }
        }
        Plan plan = Entrance.createSimplePlan(modelId);
        PLANS.add(plan);
        return plan;
    }

    @Override
    public void init(Context context) {
        this.context = context;
        FileDownloader.setup(context);
        contentObserver = new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange) {
                Log.i(Entrance.TAG, "onChange: 刷新等待");
                loadInfos.clear();
                loadInfos.addAll(
                        BusinessUtil.reflectCursor(
                                getContext().getContentResolver().query(DownLoadProvider.CONTENT_QUERY_StATUS_URI,
                                        null,
                                        "status = ?",
                                        new String[]{
                                                String.valueOf(IBasicInfo.WAITTING_STATUS),
                                        },
                                        null,
                                        null), DownLoadInfo.class));

                if (loadInfos.size() > 0) {
                    List<DownLoadInfo> collections = new ArrayList<>(loadInfos);
                    DownLoadInfo thisDownloadInfo = null;
                    for (DownLoadInfo d : collections) {
                        /**
                         * 优先找到当前的选择的下载
                         */
                        if (d.getId() == thisDownLoad) {
                            thisDownloadInfo = d;
                            break;
                        }
                    }

                    if(thisDownloadInfo == null) {
                        download2(collections.get(0));
                    }else {
                        download2(thisDownloadInfo);
                    }
                }
            }
        };
        scanner();
        getContext().getContentResolver().registerContentObserver(DownLoadProvider.CONTENT_QUERY_StATUS_URI, true, contentObserver);
    }

    private void scanner() {
        WorkUtil.scannerDoingStatusException();
    }

    /**
     * 暂停
     *
     * @param tableId 数据库 下载表 id
     */
    @Override
    public void pause(final int tableId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                userDownLoadImp(tableId).pause();
            }
        });
    }

    /**
     * 下载
     *
     * @param tableId
     */
    @Override
    public synchronized void download(int tableId) {
        /**
         * 先改为等待状态，之后刷新数据库 contentObserver
         */
        thisDownLoad = tableId;
        BusinessWrap.waitting(tableId);
    }

    /**
     * 真正的下载
     *
     * @param downLoadInfo
     */
    private synchronized void download2(DownLoadInfo downLoadInfo) {

        if (getRunningPlan() < MAX_DOWNLOAD_COUNT) {
            handler.post(userDownLoadImp(downLoadInfo.getId()));
        }

    }

    /**
     * 删除
     *
     * @param tableId
     */
    @Override
    public void delete(final int tableId, final boolean isDeleteFile) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Plan plan = userDownLoadImp(tableId);
                plan.delete(isDeleteFile);
                PLANS.remove(plan);
            }
        });

    }

    @Override
    public void reset(final int tableId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                userDownLoadImp(tableId).reset();
            }
        });
    }

    /**
     * 添加一个新任务
     *
     * @param downLoadTable
     */
    @Override
    public void addTask(DownLoadInfo downLoadTable) {
        BusinessWrap.addDownloadTask(downLoadTable);
    }

    @Override
    public void pauseAll() {
        BusinessWrap.pauseAll();
    }

    @Override
    public void deleteSavePath(String savePath) {
        BusinessWrap.deleteSavePath(savePath);
    }

    static Set<OperatorRespone> getOperatorRespones() {
        return downLoadViewController.operatorRespones;
    }

    @Override
    public void addOperatorRespone(OperatorRespone operatorRespone) {
        if (operatorRespone != null) {
            operatorRespones.add(operatorRespone);
        }
    }

    @Override
    public void removeOperatorRespone(OperatorRespone operatorRespone) {
        if (operatorRespone != null) {
            operatorRespones.remove(operatorRespone);
        }
    }

    static Context getContext() {
        return downLoadViewController.context;
    }

    private int getRunningPlan() {
        int i = 0;
        for (Plan plan : PLANS) {
            if (plan.isRunning()) {
                i++;
            }
        }
        return i;
    }
}
