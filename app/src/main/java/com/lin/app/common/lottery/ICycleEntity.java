package com.lin.app.common.lottery;

/**
 * Created by linhui on 2017/10/25.
 */
public interface ICycleEntity extends Identity,ISwitch{


    long getStartTime();

    long getEndTime();

}
