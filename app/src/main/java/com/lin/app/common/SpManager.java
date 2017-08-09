package com.lin.app.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.lin.alllib.data.respone.CityRespone;
import com.lin.app.MyApplication;

/**
 * Created by linhui on 2017/8/9.
 */
public class SpManager {

    public static final String CITYS_SP = "citys";
    public static final String CITYS= CITYS_SP;

    public static CityRespone getCitys(){
        SharedPreferences sharedPreferences = MyApplication.$THIS.getSharedPreferences(CITYS_SP, Context.MODE_PRIVATE);
        String values = sharedPreferences.getString(CITYS,"-1");
        if(!"-1".equals(values)){
            Gson gson = new Gson();
            return gson.fromJson(values,CityRespone.class);
        }else{
            return null;
        }
    }

    public static void saveCitys(CityRespone cityRespone){
        SharedPreferences sharedPreferences = MyApplication.$THIS.getSharedPreferences(CITYS_SP, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(CITYS,new Gson().toJson(cityRespone)).apply();
    }

}
