package com.feimomideaencode;

import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.os.Handler;

/**
 * Created by linhui on 2018/3/30.
 */
public class MoniTest implements IConfig {

    private Context context;
//    private

    public MoniTest(Context context){
        this.context = context;
    }


    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getFps() {
        return 0;
    }

    @Override
    public int getframe() {
        return 0;
    }

    @Override
    public String filePath() {
        return null;
    }

    @Override
    public VirtualDisplay getVirtualDisplay() {
        return null;
    }

    @Override
    public Handler getHandler() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public int getBitRate() {
        return 0;
    }
}
