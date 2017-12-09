package y.com.sqlitesdk.framework.interface_model;

import java.io.Serializable;

/**
 * Created by lpds on 2017/4/14.
 */
public interface IModel<T> extends Cloneable,Serializable{

    T clone();

    String getTableName();

    int getId();

    void setId(int id);

    String getCreateTime();

}
