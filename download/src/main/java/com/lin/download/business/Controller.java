package com.lin.download.business;

import android.content.Context;

import com.lin.download.basic.Plan;
import com.lin.download.business.Install;
import com.lin.download.business.Operator;
import com.lin.download.business.model.DownLoadInfo;

/**
 * Created by linhui on 2017/12/11.
 */
public interface Controller {

    int MAX_DOWNLOAD_COUNT = 2;

    void init(Context context);
    void pause(String objectId);
    void download(String  objectId);
    void delete(String  objectId,boolean isDeleteFile);
    void reset(String  objectId);
    void addTask(DownLoadInfo downLoadTable);
    void pauseAll();
    void deleteSavePath(String savePath);
    void removePlan(Plan plan);
    void releaseAll();
    Operator getOperator();
    Install getInstall();
}
