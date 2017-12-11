package com.lin.download.basic;

import com.lin.download.business.model.UrlInfo;

import java.io.Serializable;

/**
 * Created by linhui on 2017/12/7.
 */
public interface IBasicInfo extends UrlInfo{

    int WAITTING = 150;
    int PAUSE_STATUS = 100;
    int COMPLETED_STATUS = 200;
    int DOING_STATUS = 201;
    int ERROR_STATUS = 402;
    int NOT_HAD_STATUS = 400;

    int GAME_FEIMO_TYPE = 1000;
    int PIC_TYPE = 1001;
    int MUSIC_TYPE = 1050;
    int VIDEO_TYPE = 1100;

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
