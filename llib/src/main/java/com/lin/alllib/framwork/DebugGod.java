package com.lin.alllib.framwork;

import android.util.Log;

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

}
