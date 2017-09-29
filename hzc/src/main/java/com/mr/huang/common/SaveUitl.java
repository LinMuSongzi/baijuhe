package com.mr.huang.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.mr.huang.data.entity.NumberAllEntity;
import com.mr.huang.framwork.AppManager;

/**
 * Created by linhui on 2017/9/29.
 */
public class SaveUitl {

    private static final String XML_NAME = "hzc_name";
    private static final String NUMBER_MAIN_KEY = "number";

    public static void saveNumberList(Object collection){
        getSharedPreferences().edit().putString(NUMBER_MAIN_KEY,new Gson().toJson(collection)).apply();

    }

    public static NumberAllEntity getNumberList(){
        String json = getSharedPreferences().getString(NUMBER_MAIN_KEY,"");
        if(json.isEmpty()){
            return null;
        }
        return JsonCommon.convert(NumberAllEntity.class,json);
    }


    private static SharedPreferences getSharedPreferences(){

        return AppManager.getInstance().getApplicationContext().getSharedPreferences(XML_NAME, Context.MODE_PRIVATE);


    }


}
