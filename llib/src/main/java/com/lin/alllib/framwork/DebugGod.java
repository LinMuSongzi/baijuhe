package com.lin.alllib.framwork;

import android.os.Build;
import android.util.Log;

import com.lin.alllib.BuildConfig;

/**
 * Created by lpds on 2017/7/26.
 */
public final class DebugGod {

    public static final boolean DEBUG = true;

    public static void i(String tag,String message){
        if(DEBUG){

            Log.i(tag, message);

        }
    }

    public static boolean isDebug(){

        return false;
    }

}
