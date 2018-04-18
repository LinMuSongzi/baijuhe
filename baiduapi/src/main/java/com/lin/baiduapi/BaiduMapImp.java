package com.lin.baiduapi;

import android.content.Context;

/**
 * Created by linhui on 2018/3/28.
 */
public class BaiduMapImp implements IBaiduMap {


    public static final IBaiduMap BAIDU_MAP_IMP = new BaiduMapImp();

    private BaiduMapImp(){}

    public static IBaiduMap getInstance(){
        return BAIDU_MAP_IMP;
    }

    private Context context;

    public Context getContext(){
        return context;
    }

    @Override
    public void init(Context context) {
        this.context = context;
    }

}
