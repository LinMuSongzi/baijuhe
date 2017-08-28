package y.com.sqlitesdk.framework.business.support;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.entity.respone.ModifyRespone;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/8/25.
 */
public class Regime<T extends IModel<T>> implements Plant{
    T model;
    String action;
    String tableName;
    List<Child> wheres = new LinkedList<>();
    List<Child> selections = new LinkedList<>();
    public Regime(T model) {
        this.model = model;
        this.tableName = model.getTableName();
    }

    public Plant select(Child child){
        selections.add(child);
        return this;
    }

    public Plant wheres(Child child){
        wheres.add(child);
        return this;
    }

    public void execute(){
        Access.run(this);
    }

    @Override
    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
    }

    @Override
    public void onExternalError() {

    }
}
