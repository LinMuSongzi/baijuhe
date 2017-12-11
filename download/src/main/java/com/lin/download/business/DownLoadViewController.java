package com.lin.download.business;

import com.lin.download.basic.DownLoadExpressPlan;
import com.lin.download.basic.provide.table.DownLoadTable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by linhui on 2017/12/11.
 */
public class DownLoadViewController {


    private static DownLoadViewController downLoadViewController;

    static {
        downLoadViewController = new DownLoadViewController();
    }

    private DownLoadViewController() {
    }

    public static DownLoadViewController getInstance() {
        return downLoadViewController;
    }


    private final Set<DownLoadExpressPlan> PLANS = new HashSet<DownLoadExpressPlan>() {
        @Override
        public boolean add(DownLoadExpressPlan o) {
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


    private DownLoadExpressPlan userDownLoadImp(int id) {

        synchronized (this) {
            for (DownLoadExpressPlan downLoadImp : PLANS) {

                if (downLoadImp.getId() == id) {
                    return downLoadImp;
                }

            }
        }
        return null;
    }

    public void pause(int id) {
        DownLoadExpressPlan downLoadImp = userDownLoadImp(id);
        if (downLoadImp != null) {
            downLoadImp.pause();
        }
    }


    public void download(int id) {
        Runnable runnable;
        DownLoadExpressPlan plan = userDownLoadImp(id);
        if (plan != null) {
            runnable = plan;
        } else {
            DownLoadExpressPlan plan1 = new DownLoadExpressPlan(id);
            PLANS.add(plan1);
            runnable = plan1;
        }
        new Thread(runnable).start();
    }


    public void delete(int id) {
        DownLoadExpressPlan plan = userDownLoadImp(id);
        if (plan != null) {
            plan.delete();
            PLANS.remove(plan);
        }else{

        }
    }

    public void reset(int id) {
        DownLoadExpressPlan plan = userDownLoadImp(id);
        if (plan != null) {
            plan.reset();
        }
    }

    public void addTask(DownLoadTable downLoadTable) {

        DownloadBusiness.addDownloadTask(downLoadTable);

    }


}
