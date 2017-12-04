package com.yeyuanyuan.imp;

import android.database.sqlite.SQLiteDatabase;

import com.yeyuanyuan.commander.Plan;
import com.yeyuanyuan.commander.Reserve;

import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/12/4.
 */
final class HalePlan<T> implements Plan<T>, Execute {

    private Materials materials;
    private Reserve r;

    public <T> HalePlan(Class<T> tClass) {
        materials = new Materials();
    }

    @Override
    public Plan where(String[] parameters, String[] values) {
        materials.whereParameters = parameters;
        materials.whereParaValues = values;
        return this;
    }

    @Override
    public Plan orderBy(boolean isDesc) {
        materials.isDesc = isDesc;
        return this;
    }

    @Override
    public Plan column(String[] columns) {
        materials.columns = columns;
        return this;
    }

    @Override
    public Plan execute(Reserve reserve) {
        this.r = reserve;
        Access.run(this);
        return this;
    }

    @Override
    public String getTbName() {
        return materials.tbName;
    }

    private String buildSQL() {
        return null;
    }

    @Override
    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
        switch (r.type()) {
            case Reserve.DELETE:
                delete(sqLiteDatabase);
                break;
            case Reserve.INSERT:
                insert(sqLiteDatabase);
                break;
            case Reserve.QUERY:
                query(sqLiteDatabase);
                break;
            case Reserve.UPDATE:
                update(sqLiteDatabase);
                break;
        }
    }

    private void update(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.update(getTbName(),)
    }

    private void query(SQLiteDatabase sqLiteDatabase) {

    }

    private void insert(SQLiteDatabase sqLiteDatabase) {
    }

    private void delete(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.delete(getTbName(), materials.whereStr, materials.whereParaValues);
    }

    @Override
    public void onExternalError() {

    }

    public static final class Materials {
        private String tbName;
        private boolean isDesc;
        private String[] whereParameters;
        private String[] whereParaValues;
        private String[] columns;

        private String whereStr;

    }

}
