package com.lin.download.business;

import com.lin.download.basic.DownLoadExpressPlan;
import com.lin.download.basic.Plan;
import com.lin.download.basic.provide.table.DownLoadTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by linhui on 2017/12/11.
 */
public class DownLoadViewController implements Controller{


    private static DownLoadViewController downLoadViewController;

    static {
        downLoadViewController = new DownLoadViewController();
    }

    private DownLoadViewController() {
    }

    public static Controller getInstance() {
        return downLoadViewController;
    }

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

    public void pause(int tableId) {
        Plan downLoadImp = userDownLoadImp(tableId);
        if (downLoadImp != null) {
            downLoadImp.pause();
        }
    }

    public void download(int tableId) {
        Runnable runnable;
        Plan plan = userDownLoadImp(tableId);
        if (plan != null) {
            runnable = plan;
        } else {
            Plan plan1 = new DownLoadExpressPlan(tableId);
            PLANS.add(plan1);
            runnable = plan1;
        }
        new Thread(runnable).start();
    }

    public void delete(int tableId) {
        Plan plan = userDownLoadImp(tableId);
        if (plan != null) {
            plan.delete();
            PLANS.remove(plan);
        }else{

        }
    }

    public void reset(int tableId) {
        Plan plan = userDownLoadImp(tableId);
        if (plan != null) {
            plan.reset();
        }
    }

    public void addTask(DownLoadTable downLoadTable) {

        DownloadBusiness.addDownloadTask(downLoadTable);

    }


}
