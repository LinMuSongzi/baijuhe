package y.com.sqlitesdk.framework.business;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import y.com.sqlitesdk.framework.interface_model.IModel;
import y.com.sqlitesdk.framework.util.StringDdUtil;

/**
 * Created by lpds on 2017/4/15.
 */
@Deprecated
public final class Business {

    /**
     * FOREIGN KEY(trackartist) REFERENCES artist(artistid)
     * 外键
     */

    private static final String TAG = Business.class.getSimpleName();
    private static Business business;

    static {
        business = new Business();
    }

    private Business() {

    }

    public static final Business getInstances() {
        return business;
    }

    /**
     * 创建表格
     *
     * @param tClass
     * @param <T>
     */
    public final <T extends IModel> boolean createTable(SQLiteDatabase sqLiteDatabase, final Class<T> tClass) throws Exception {
        String tb_name = BusinessUtil.getTbNmae(tClass);
        String[] sqls;
        sqls = BusinessUtil.getTableStr(tb_name, tClass);
        sqLiteDatabase.execSQL(sqls[0]);
        Log.i(TAG, "onExecute: " + sqls[0]);

        final String[] sqltrigger = sqls.clone();
        final int lenght = sqltrigger.length;
        if (sqltrigger.length > 1) {
            for (int i = 1; i < lenght; i++) {
                sqLiteDatabase.execSQL(sqltrigger[i]);
                Log.i(TAG, "onExecute: " + sqltrigger[i]);
            }
        }

        return true;

    }

    /**
     * 新增一行数据
     *
     * @param model
     * @param <T>
     */
    public <T extends IModel> long insert(SQLiteDatabase sqLiteDatabase, T model) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        model.setId((int) checkInsert(sqLiteDatabase, model));
        return model.getId();
    }


    public <T extends IModel> long insertNoReplace(SQLiteDatabase sqLiteDatabase, T model)throws IllegalAccessException, NoSuchFieldException, InstantiationException{
        model.setId((int) checkInsertNoReplace(sqLiteDatabase, model));
        return model.getId();
    }




    /**
     * 新增一组数据
     *
     * @param models
     * @param <T>
     */

    public <T extends IModel> boolean insert(SQLiteDatabase sqLiteDatabase, List<T> models) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        for (T model : models) {
            model.setId((int) checkInsert(sqLiteDatabase, model));
        }
        return true;
    }


    private <T extends IModel> long checkInsertNoReplace(SQLiteDatabase sqLiteDatabase, T model) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        ContentValues contentvalues = BusinessUtil.getAllValues(model);
        Field[] fields = BusinessUtil.getAllUniqueFields(model);
        String tbName = BusinessUtil.getTbNmae(model.getClass());
        if (fields.length == 0) {
            return sqLiteDatabase.insert(tbName, null, contentvalues);
        }
        String sqlExecute = "SELECT * FROM " + tbName;
        String where = "";
        List<String> values = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            final String value = BusinessUtil.convertField(fields[i], model);
            if (!StringDdUtil.isNull(value)) {
                if (!sqlExecute.contains("WHERE")) {
                    sqlExecute += " WHERE ";
                }
                values.add(value);
                where += fields[i].getName() + " = ?";
                where += " AND ";
            }
        }
        if (values.size() > 0) {
            where = where.substring(0, where.length() - 4);

            Cursor c = sqLiteDatabase.rawQuery(sqlExecute + where, values.toArray(new String[values.size()]));
            if (c.getCount() == 1) {
                Log.i(TAG, "checkInsert: 已在在");
                model.setId(BusinessUtil.reflectCursorOne(c,model.getClass(),true).getId());
                return model.getId();
//                if(sqLiteDatabase.update(BusinessUtil.getTbNmae(model.getClass()), contentvalues, where, values.toArray(new String[values.size()]))>0){
//                    return Business.getInstances().queryLineByWhere(sqLiteDatabase,model.getClass(),where, values.toArray(new String[values.size()])).getId();
//                }
            }
        }
        return (int)sqLiteDatabase.insert(BusinessUtil.getTbNmae(model.getClass()), null, contentvalues);

    }

    /**
     * 判断唯一值是否需要新增
     *
     * @param sqLiteDatabase
     * @param model
     * @param <T>
     */
    private <T extends IModel> long checkInsert(SQLiteDatabase sqLiteDatabase, T model) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        ContentValues contentvalues = BusinessUtil.getAllValues(model);
        Field[] fields = BusinessUtil.getAllUniqueFields(model);
        String tbName = BusinessUtil.getTbNmae(model.getClass());
        if (fields.length == 0) {
            return sqLiteDatabase.insert(tbName, null, contentvalues);
        }
        String sqlExecute = "SELECT * FROM " + tbName;
        String where = "";
        List<String> values = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            final String value = BusinessUtil.convertField(fields[i], model);
            if (!StringDdUtil.isNull(value)) {
                if (!sqlExecute.contains("WHERE")) {
                    sqlExecute += " WHERE ";
                }
                values.add(value);
                where += fields[i].getName() + " = ?";
                where += " AND ";
            }
        }
        if (values.size() > 0) {
            where = where.substring(0, where.length() - 4);

            Cursor c = sqLiteDatabase.rawQuery(sqlExecute + where, values.toArray(new String[values.size()]));
            if (c.getCount() == 1) {
                Log.i(TAG, "checkInsert: 已在在，进行修改" + sqlExecute + where);
                if(sqLiteDatabase.update(BusinessUtil.getTbNmae(model.getClass()), contentvalues, where, values.toArray(new String[values.size()]))>0){
                    return Business.getInstances().queryLineByWhere(sqLiteDatabase,model.getClass(),where, values.toArray(new String[values.size()])).getId();
                }
            }
        }
        return (int)sqLiteDatabase.insert(BusinessUtil.getTbNmae(model.getClass()), null, contentvalues);


    }

    /**
     * 删除一行数据，根据id
     *
     * @param model
     * @param <T>
     */
    @Deprecated
    public <T extends IModel<T>> long deleteById(SQLiteDatabase sqLiteDatabase, T model) throws IllegalAccessException, InstantiationException {
        return delete(sqLiteDatabase, model, String.format("%s = %s", "id", String.valueOf(model.getId())),null);
    }

    /**
     * 删除一行数据
     *
     * @param model
     * @param where
     * @param <T>
     */
    public <T extends IModel> long delete(SQLiteDatabase sqLiteDatabase, T model, final String where,String[] args) throws InstantiationException, IllegalAccessException {
        return sqLiteDatabase.delete(BusinessUtil.getTbNmae(model.getClass()), where, args);
    }


    /**
     * 删除表格所有数据
     *
     * @param tablename
     */
    public boolean deleteAll(SQLiteDatabase sqLiteDatabase, String tablename) {
        sqLiteDatabase.delete(tablename, null, null);
        return true;
    }

    /**
     * 更新
     *
     * @param model
     * @param <T>
     */

    public <T extends IModel<T>> long modify(SQLiteDatabase sqLiteDatabase, T model) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return modify(sqLiteDatabase, model, String.format(" %s = %s ", "id", String.valueOf(model.getId())),null);
    }


    public <T extends IModel<T>> long modify(SQLiteDatabase sqLiteDatabase, T model, final String where,String[] agrs) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        ContentValues contentValues = BusinessUtil.getAllValues(model);
        long id = -1;
        if(sqLiteDatabase.update(BusinessUtil.getTbNmae(model.getClass()), contentValues, where, agrs) == 1){
            id = this.queryLineByWhere(sqLiteDatabase, model.getClass(),where, agrs).getId();
        }
        model.setId((int) id);
        return id;
    }

//    public void modifyBySQL(SQLiteDatabase sqLiteDatabase,String SQLstr,String[] args){
//        sqLiteDatabase.execSQL(SQLstr,args);
//    }


    public <T extends IModel> T queryById(SQLiteDatabase sqLiteDatabase,T model) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        T queryModel = null;
        Cursor cursor = sqLiteDatabase.query(BusinessUtil.getTbNmae(model.getClass()), null, "id = " + model.getId(), null, null, null, null);
        if (cursor.getCount() == 1) {
            queryModel = (T) BusinessUtil.reflectCursorOne(cursor, model.getClass(),true);
        }
        return queryModel;
    }

    public <T extends IModel> T queryById(SQLiteDatabase sqLiteDatabase, int id,Class<T> tClass) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        T queryModel = null;
        Cursor cursor = sqLiteDatabase.query(BusinessUtil.getTbNmae(tClass),
                null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.getCount() == 1) {
            queryModel = BusinessUtil.reflectCursorOne(cursor, tClass,true);
        }
        return queryModel;
    }

    public <T extends IModel> T queryLineByWhere(SQLiteDatabase sqLiteDatabase, final Class<T> tClass,String where,String[] whereArgs) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        T queryModel = null;
        Cursor cursor = sqLiteDatabase.query(BusinessUtil.getTbNmae(tClass), null, where, whereArgs, null, null, null);
        if (cursor.getCount() == 1) {
            queryModel = BusinessUtil.reflectCursor(cursor, tClass).get(0);
        }
        return queryModel;
    }

    public <T extends IModel<T>> List<T> queryAll(SQLiteDatabase sqLiteDatabase, final Class<T> tClass,String where,String[] whereArgs) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        Cursor cursor = sqLiteDatabase.query(BusinessUtil.getTbNmae(tClass), null, where, whereArgs, null, null, null);
        return BusinessUtil.reflectCursor(cursor, tClass);
    }

    /**
     * @param tClass
     * @param sql
     * @param <T>
     */
    public <T extends IModel<T>> List<T> executeSQLQuery(SQLiteDatabase sqLiteDatabase, final Class<T> tClass, final String sql) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return BusinessUtil.reflectCursor(cursor, tClass);
    }

    /**
     * @param tClass
     * @param sql
     * @param <T>
     */
    public <T extends IModel<T>> Cursor executeSQLQueryConvertCursor(SQLiteDatabase sqLiteDatabase, final Class<T> tClass, final String sql) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        return sqLiteDatabase.rawQuery(sql, null);
    }

    public <T extends IModel<T>> long getTableMaxCount(SQLiteDatabase sqLiteDatabase, Class<T> tClass, String where) throws InstantiationException, IllegalAccessException {
        String tbName = BusinessUtil.getTbNmae(tClass);
        Cursor cursor = null;
        if (!StringDdUtil.isNull(where)) {
            cursor = sqLiteDatabase.rawQuery(String.format("SELECT count(*) FROM %s WHERE " + where, tbName), null);
        } else {
            cursor = sqLiteDatabase.rawQuery(String.format("SELECT count(*) FROM %s ", tbName), null);
        }

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();
            final int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;
    }


    public <T extends IModel<T>> T  omnipotentSQL(
            SQLiteDatabase sqLiteDatabase,
            Class<T> tClass,
            String[] fields,
            String[] values) throws IllegalAccessException, InstantiationException {
        T model = tClass.newInstance();
        return model;
    }

    /**
     * 获取某列的所有值
     * @param sqLiteDatabase
     * @param field
     * @param where
     * @param args
     * @return
     */
    public int getMaxByField(SQLiteDatabase sqLiteDatabase,String field,String table,String where,String[] args){
        int maxUnReadCount = 0;
        String sql;
//        if(StringDdUtil.isNull(where)){
//            sql = String.format("SELECT sum(%s) FROM  %s", field, table);
//        }else {
            sql = String.format("SELECT sum(%s) FROM  %s where %s", field, table, where);
//        }
        Cursor cursor = sqLiteDatabase.rawQuery(sql,args);
        if (cursor != null && cursor.moveToFirst()) {
            maxUnReadCount = cursor.getInt(0);
        }
        return maxUnReadCount;
    }

}
