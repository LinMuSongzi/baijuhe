package com.lin.download.basic.provide;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.lin.download.BuildConfig;
import com.lin.download.basic.DonwloadSqlLiteOpenHelp;
import com.lin.download.basic.Entrance;
import com.lin.download.business.model.DownLoadInfo;

import y.com.sqlitesdk.framework.AppMain;
import y.com.sqlitesdk.framework.IfeimoSqliteSdk;
import y.com.sqlitesdk.framework.business.Business;
import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;
import y.com.sqlitesdk.framework.util.StringDdUtil;

/**
 * Created by linhui on 2017/12/7.
 */
public final class DownLoadProviderImp {

    private Context mContext;

    public DownLoadProviderImp(Context mContext) {
        this.mContext = mContext;
    }

    boolean create() {
        return true;
    }

    Cursor query(Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {

        final Cursor[] cursor = new Cursor[1];

        switch (DownLoadProvider.matcher.match(uri)) {
            case DownLoadProvider.QUERY_ALL_CODE:
                Access.runCustomThread(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                        String sortOrder_ = sortOrder;
                        if(StringDdUtil.isNull(sortOrder)){
                            sortOrder_ = "create_time DESC";
                        }

                        cursor[0] = sqLiteDatabase.query(DownLoadInfo.TB_NAME,
                                projection, selection, selectionArgs, null, null, sortOrder_, null);
                        cursor[0].setNotificationUri(mContext.getContentResolver(), DownLoadProvider.CONTENT_QUERY_ALL_URI);
                    }

                    @Override
                    public void onExternalError() {

                    }
                });
                break;
            case DownLoadProvider.QUERY_StATUS_CODE:

                Access.runCustomThread(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                        cursor[0] = sqLiteDatabase.query(DownLoadInfo.TB_NAME,
                                projection, selection, selectionArgs, null, null, sortOrder, null);
                        cursor[0].setNotificationUri(mContext.getContentResolver(), DownLoadProvider.CONTENT_QUERY_StATUS_URI);
                    }

                    @Override
                    public void onExternalError() {

                    }
                });

                break;
        }
        return cursor[0];
    }

    @Nullable
    String getType(Uri uri) {
        return null;
    }

    @Nullable
    Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    int delete(Uri uri, final String selection, final String[] selectionArgs) {

        final int[] leng = {0};

        switch (DownLoadProvider.matcher.match(uri)) {

            case DownLoadProvider.DELETE_ONE_CODE:

                Access.run(new Execute() {
                    @Override
                    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                        leng[0] = sqLiteDatabase.delete(DownLoadInfo.TB_NAME, selection, selectionArgs);
                        if (leng[0] > 0) {
                            mContext.getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, null);
                        }
                    }

                    @Override
                    public void onExternalError() {

                    }
                });

                break;

        }


        return leng[0];
    }

    int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
