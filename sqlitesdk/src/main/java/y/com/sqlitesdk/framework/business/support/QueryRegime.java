package y.com.sqlitesdk.framework.business.support;

import android.database.sqlite.SQLiteDatabase;

import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/9/2.
 */
public class QueryRegime<T extends IModel> extends Regime<T> {
    protected QueryRegime(T t) {
        super(t);
    }

    @Override
    public String buildSql() {
        return null;
    }

    @Override
    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {

    }
}
