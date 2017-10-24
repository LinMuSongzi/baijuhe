package com.mr.huang.common;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.mr.huang.data.entity.NumberAllEntity;
import com.mr.huang.framwork.AppManager;

/**
 * Created by linhui on 2017/9/29.
 */
public class SaveUitl {

    private static final String XML_NAME = "hzc_name";
    private static final String NUMBER_MAIN_KEY = "_number";
    private static final String TAG = "SaveUitl";

    public static void saveNumberList(Object collection,ContextWrapper context){
        getSharedPreferences(context).edit().putString(NUMBER_MAIN_KEY,new Gson().toJson(collection)).apply();

    }

    public static NumberAllEntity getNumberList(ContextWrapper context){
        String json = getSharedPreferences(context).getString(NUMBER_MAIN_KEY,"");
        if(json.isEmpty()){
            return null;
        }
        Log.i(TAG, "getNumberList: "+json);
        return JsonCommon.convert(NumberAllEntity.class,json);
    }

    public static void clearNumberList(ContextWrapper context){
        getSharedPreferences(context).edit().clear().apply();
    }



    private static SharedPreferences getSharedPreferences(ContextWrapper contextWrapper){
        return contextWrapper.getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);


    }


}
