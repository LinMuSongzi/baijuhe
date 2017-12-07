package com.lin.download.basic.provide;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.lin.download.BuildConfig;
import com.lin.download.basic.Factory;
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
public final class DownLoadProviderImp implements AppMain {

    private DatabaseErrorHandler mDatabaseErrorHandler;
    @Deprecated
    private SQLiteDatabase mSqLiteDatabase = null;
    private Context mContext;

    public DownLoadProviderImp(Context mContext) {
        this.mContext = mContext;
    }

    boolean create() {
        try {
            mDatabaseErrorHandler = new DatabaseErrorHandler() {
                @Override
                public void onCorruption(SQLiteDatabase dbObj) {
                    Log.i(Factory.TAG, "sqlite onCorruption: ");
                }
            };
            mSqLiteDatabase = mContext.openOrCreateDatabase(DownLoadProvider.DOWNLOAD_DB_NAME, Context.MODE_PRIVATE, null, mDatabaseErrorHandler);
            IfeimoSqliteSdk.init(this);
            checkUpdateSqlite();
        } catch (Exception e) {
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

    Cursor query(Uri uri, String[] projection, final String selection, final String[] selectionArgs, String sortOrder) {

        final Cursor[] cursor = new Cursor[1];

        switch (DownLoadProvider.matcher.match(uri)) {
            case DownLoadProvider.QUERY_ALL_CODE:
                Access.runCustomThread(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                        cursor[0] = sqLiteDatabase.rawQuery("select * from " + DownLoadTable.TB_NAME, null);
                    }

                    @Override
                    public void onExternalError() {

                    }
                });
                break;
        }
        return cursor[0];
    }

    private synchronized void checkUpdateSqlite() {


        SharedPreferences sharedPreferences =
                mContext.getSharedPreferences("DownLoadProviderImp", Context.MODE_PRIVATE);
        final int code = sharedPreferences.getInt("code", -1);
        if (code < BuildConfig.VERSION_CODE) {
            updateSqlite(code, BuildConfig.VERSION_CODE);
            sharedPreferences.edit().putInt("code", BuildConfig.VERSION_CODE).apply();
        }
    }

    private void updateSqlite(int thisV, int newV) {

        switch (thisV) {
            case -1:
                switch (newV) {
                    case 20171121:
                        createTable();
                        break;
                }
                break;
            case 20171121:
                break;
        }


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