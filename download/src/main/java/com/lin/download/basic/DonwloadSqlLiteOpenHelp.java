package com.lin.download.basic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lin.download.BuildConfig;
import com.lin.download.business.model.DownLoadInfo;

import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.db.Access;

/**
 * Created by linhui on 2017/12/12.
 */
public class DonwloadSqlLiteOpenHelp extends SQLiteOpenHelper {

    public DonwloadSqlLiteOpenHelp(Context context) {
        super(context, "tb_download.db", null, BuildConfig.VERSION_CODE);
    }


    public DonwloadSqlLiteOpenHelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            Business.getInstances().createTable(sqLiteDatabase, DownLoadInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
