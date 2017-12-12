package com.lin.download.business;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.lin.download.basic.Entrance;
import com.lin.download.basic.Plan;
import com.lin.download.business.model.DownLoadInfo;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.HashSet;
import java.util.Set;

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

    private WorkController() {
        HandlerThread handlerThread = new HandlerThread("DownLoadViewController");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    Handler getHandler() {
        return handler;
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
    public void download(int tableId) {
        handler.post(userDownLoadImp(tableId));
    }

    /**
     * 删除
     *
     * @param tableId
     */
    @Override
    public void delete(final int tableId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Plan plan = userDownLoadImp(tableId);
                plan.delete();
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
}
