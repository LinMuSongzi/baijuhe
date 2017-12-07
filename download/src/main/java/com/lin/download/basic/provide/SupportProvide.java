package com.lin.download.basic.provide;


import android.content.Context;

import com.lin.download.basic.IBasicInfo;

import y.com.sqlitesdk.framework.business.CenterServer;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/7.
 */
public class SupportProvide {


    public static void queryLine(Context context, IBasicInfo basicInfo,Runnable r){


    }

    public static void insertOne(IBasicInfo info) {
        CenterServer.getInstances().insert((IModel)info);
    }
}
