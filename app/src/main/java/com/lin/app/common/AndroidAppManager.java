package com.lin.app.common;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;

import com.lin.alllib.AppLife;
import com.lin.alllib.framwork.manager.ActivityManager;
import com.lin.app.data.entity.AppEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linhui on 2017/9/14.
 */
public final class AndroidAppManager {


    private static AndroidAppManager androidAppManager = new AndroidAppManager();

    private Handler handler;

    private List<AppEntity> list = new ArrayList<>();

    private AndroidAppManager() {
        HandlerThread h= new HandlerThread(getClass().getSimpleName());
        h.start();
        handler = new Handler(h.getLooper());
    }


    public static AndroidAppManager getInstance() {
        return androidAppManager;
    }

    public void postAndroidApp() {
        postAndroidApp(null);
    }

    public  void postAndroidApp(final String filter) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(list.size() == 0){
                    EventBus.getDefault().post(androidAppManager.getAllAndroidApp2());
                }else{
                    EventBus.getDefault().post(androidAppManager.getAllAndroidApp3(filter));
                }
            }
        });
    }

    private List<AppEntity> getAllAndroidApp3(String filter) {
        List<AppEntity> list = new LinkedList<>();
        for(AppEntity appEntity : this.list){

            AppEntity a = new AppEntity();
            a.setPackageName(appEntity.getPackageName());
            a.setAppname(appEntity.getAppname());

            if (!a.getAppname().contains(filter) && filter != null) {
                continue;
            }

            a.setVersionCode(appEntity.getVersionCode());
            a.setIcon(appEntity.getIcon());
            a.setFirstInstallTime(appEntity.getFirstInstallTime());
            list.add(a);

        }
        return list;
    }

    private List<AppEntity> getAllAndroidApp2() {

        PackageManager pm = AppLife.getInstance().getApplication().getPackageManager();
        List<AppEntity> list = new LinkedList<>();

        for (PackageInfo p : pm.getInstalledPackages(PackageManager.GET_ACTIVITIES)) {
            AppEntity a = new AppEntity();
            a.setPackageName(p.packageName);
            a.setAppname((String) pm.getApplicationLabel(p.applicationInfo));
            a.setVersionCode(p.versionName);
            a.setIcon(pm.getApplicationIcon(p.applicationInfo));
            a.setFirstInstallTime(p.firstInstallTime);
            list.add(a);
        }
        this.list.clear();
        this.list.addAll(new ArrayList<AppEntity>(list));
        return list;
    }


    public void startApp(String packageName){

        PackageManager packageManager = AppLife.getInstance().getApplication().getPackageManager();

        Intent intent = packageManager.getLaunchIntentForPackage(packageName);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;

        AppLife.getInstance().getApplication().startActivity(intent);
    }



}
