package com.lin.download.basic;

import java.io.Serializable;

import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/7.
 */
public interface IBasicInfo extends Cloneable,Serializable{


    int PAUSE = 100;
    int COMPLETED = 200;
    int DOING = 201;
    int NOT_HAD = 400;

    void setDownLoadId(String id);

    String getDownLoadId();

    boolean isNotitfyShowDownLoadStutas();

    String getObjectId();

    String getName();

    String getPicUrl();

    String getDownLoadUrl();

    String getSavePath();

    long getToTalLeng();

    long getCurrentLeng();

    int getStatus();


}
