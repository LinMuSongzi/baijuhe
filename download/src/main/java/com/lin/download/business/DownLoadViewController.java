package com.lin.download.business;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.lin.download.basic.PlanImp;
import com.lin.download.basic.Plan;
import com.lin.download.business.model.DownLoadTable;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by linhui on 2017/12/11.
 */
public class DownLoadViewController implements Controller, Operator {


    static DownLoadViewController downLoadViewController;

    public static Controller getInstance() {
        return downLoadViewController;
    }

    static {
        downLoadViewController = new DownLoadViewController();
    }

    private Context context;
    private Handler handler;

    private DownLoadViewController() {
        HandlerThread handlerThread = new HandlerThread("DownLoadViewController");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
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
            synchronized (DownLoadViewController.this) {
                return super.add(o);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized (DownLoadViewController.this) {
                return super.remove(o);
            }
        }
    };

    /**
     * 根据数据库表格id得到下载计划
     * @param modelId
     * @return
     */
    private Plan userDownLoadImp(int modelId) {

        synchronized (this) {
            for (Plan downLoadImp : PLANS) {

                if (downLoadImp.getModelId() == modelId) {
                    return downLoadImp;
                }

            }
        }
        return null;
    }

    @Override
    public void init(Context context) {
        this.context = context;
        FileDownloader.setup(context);
    }

    /**
     * 暂停
     * @param tableId 数据库 下载表 id
     */
    @Override
    public void pause(int tableId) {
        Plan downLoadImp = userDownLoadImp(tableId);
        if (downLoadImp != null) {
            downLoadImp.pause();
        }
    }

    /**
     * 下载
     * @param tableId
     */
    @Override
    public void download(int tableId) {
        Runnable runnable;
        Plan plan = userDownLoadImp(tableId);
        if (plan != null) {
            runnable = plan;
        } else {
            Plan plan1 = new PlanImp(tableId);
            PLANS.add(plan1);
            runnable = plan1;
        }
        handler.post(runnable);
    }

    /**
     * 删除
     * @param tableId
     */
    @Override
    public void delete(int tableId) {
        Plan plan = userDownLoadImp(tableId);
        if (plan != null) {
            plan.delete();
            PLANS.remove(plan);
        } else {
            BusinessWrap.delete(tableId,null);
        }
    }

    @Override
    public void reset(int tableId) {
        Plan plan = userDownLoadImp(tableId);
        if (plan != null) {
            plan.reset();
        }
    }

    /**
     * 添加一个新任务
     * @param downLoadTable
     */
    @Override
    public void addTask(DownLoadTable downLoadTable) {
        BusinessWrap.addDownloadTask(downLoadTable);
    }

    @Override
    public void pauseAll() {
        FileDownloader.getImpl().pauseAll();
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
}
