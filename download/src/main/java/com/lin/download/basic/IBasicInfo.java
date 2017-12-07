package com.lin.download.basic;

import java.io.Serializable;

/**
 * Created by linhui on 2017/12/7.
 */
public interface IBasicInfo extends Cloneable,Serializable{

    void setDownLoadId(String id);

    String getDownLoadId();

    boolean isNotitfyShowDownLoadStutas();

    String getObjectId();

    String getName();

    String getPicUrl();

    String getDownLoadUrl();

    String getSavePath();


}
