package com.lin.download.basic;

import com.lin.download.business.model.UrlInfo;

import java.io.Serializable;

/**
 * Created by linhui on 2017/12/7.
 */
public interface IBasicInfo extends UrlInfo{


    int PAUSE = 100;
    int COMPLETED = 200;
    int DOING = 201;
    int ERROR = 402;
    int NOT_HAD = 400;

    void setDownLoadId(String id);

    String getDownLoadId();

    boolean isNotitfyShowDownLoadStutas();

    String getObjectId();

    String getName();

    String getPicUrl();

    String getSavePath();

    long getToTalLeng();

    long getCurrentLeng();

    int getStatus();


}
