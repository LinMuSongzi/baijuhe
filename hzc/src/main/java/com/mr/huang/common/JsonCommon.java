package com.mr.huang.common;

import com.google.gson.Gson;

/**
 * Created by linhui on 2017/9/29.
 */
public class JsonCommon {



    public static <T> T convert(Class<T>  c,String json){
        try {
            return new Gson().fromJson(json, c);
        }catch (Exception ex){
            return null;
        }
    }

}
