package com.lin.download.basic.provide;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.lin.download.basic.provide.table.DownLoadTable;

import y.com.sqlitesdk.framework.AppMain;
import y.com.sqlitesdk.framework.IfeimoSqliteSdk;
import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.business.CenterServer;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/7.
 */
public final class DownLoadProviderImp implements AppMain{

    private DatabaseErrorHandler mDatabaseErrorHandler;
    @Deprecated
    private SQLiteDatabase mSqLiteDatabase = null;
    private Context mContext;

    public DownLoadProviderImp(Context mContext) {
        this.mContext = mContext;
    }

    boolean create(){
        try {
            mDatabaseErrorHandler = new DatabaseErrorHandler() {
                @Override
                public void onCorruption(SQLiteDatabase dbObj) {
                    Log.i(DownLoadProvider.TAG, "sqlite onCorruption: ");
                }
            };
            mSqLiteDatabase = mContext.openOrCreateDatabase(DownLoadProvider.DOWNLOAD_DB_NAME, Context.MODE_PRIVATE,null,mDatabaseErrorHandler);
            IfeimoSqliteSdk.init(this);
            createTable();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void createTable() {
        try {
            CenterServer.getInstances().createTable(DownLoadTable.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (DownLoadProvider.matcher.match(uri)) {
            case DownLoadProvider.QUERY_ALL_CODE:
//                mSqLiteDatabase.query()
                break;
        }
        return cursor;
    }



    @Nullable
    String getType(Uri uri) {
        return null;
    }

    @Nullable
    Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


    @Override
    public Application getApplication() {
        return (Application) mContext.getApplicationContext();
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override
    public SQLiteDatabase getSQLiteDatabase() {
        return mSqLiteDatabase;
    }
}
