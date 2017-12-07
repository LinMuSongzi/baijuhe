package com.lin.download.basic.provide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.lin.download.basic.IBasicInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.business.BusinessUtil;
import y.com.sqlitesdk.framework.business.CenterServer;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.entity.respone.QuerySingleRespone;
import y.com.sqlitesdk.framework.interface_model.IModel;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/7.
 */
public class SupportProvide {


    public static void queryLine(Context context, IBasicInfo basicInfo){
        final T copyModel = model.clone();
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
                T queryModel;
                if((queryModel = Business.getInstances().queryById(sqLiteDatabase, copyModel)) != null){
                    EventBus.getDefault().post(new QuerySingleRespone(queryModel));
                }else{
                    EventBus.getDefault().post(new QuerySingleRespone<T>(null));
                }
            }

            @Override
            public void onExternalError() {
                EventBus.getDefault().post(null);
            }
        });
    }

    public static void insertOne(IBasicInfo info) {
        CenterServer.getInstances().insert((IModel)info);
    }
}
