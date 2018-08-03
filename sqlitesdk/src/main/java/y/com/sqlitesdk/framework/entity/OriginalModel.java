package y.com.sqlitesdk.framework.entity;

import java.security.Provider;
import java.sql.Date;

import y.com.sqlitesdk.framework.annotation.TBColumn;
import y.com.sqlitesdk.framework.annotation.TBPrimarykey;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by lpds on 2017/4/15.
 */
public abstract class OriginalModel extends Stickness implements IModel {

    @TBPrimarykey
    protected long id;
    @TBColumn(unique = true)
    protected long date;

    protected OriginalModel() {
        date = System.currentTimeMillis();
    }

    @Override
    public IModel clone() {
        return null;
    }

//    @Override
//    public long getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(long id) {
//        this.id = id;
//    }

//    @Override
//    public long getCreateTime() {
//        return date;
//    }
//
//    @Override
//    public void setCreateTime(long date) {
//        this.date = date;
//    }
}
