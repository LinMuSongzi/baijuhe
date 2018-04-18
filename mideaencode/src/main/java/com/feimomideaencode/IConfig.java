package com.feimomideaencode;

import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.os.Handler;

/**
 * Created by linhui on 2018/3/30.
 */
public interface IConfig {

    int  getWidth();

    int  getHeight();

    int getFps();

    int getframe();

    String filePath();

    VirtualDisplay getVirtualDisplay();

    Handler getHandler();

    Context getContext();

    String getPath();

    int getBitRate();
}
