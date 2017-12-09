package com.lin.download.basic.provide;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lin.download.basic.IBasicInfo;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.business.CenterServer;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.interface_model.IModel;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/7.
 */
public class SupportProvide {


    public static int queryLine(Context context, final IModel basicInfo){
        final int[] i = {0};
        Access.runCustomThread(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                i[0] = (int) Business.getInstances().queryById(sqLiteDatabase,basicInfo).getId();
            }

            @Override
            public void onExternalError() {

            }
        });
        return 0;
    }

    public static void insertOne(IBasicInfo info) {
        CenterServer.getInstances().insert((IModel)info);
    }
}
