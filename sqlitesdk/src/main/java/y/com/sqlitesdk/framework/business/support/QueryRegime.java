package y.com.sqlitesdk.framework.business.support;

import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/9/2.
 */
public class QueryRegime<T extends IModel<T>> extends Regime<T> {
    @Override
    public String buildSql() {
        return null;
    }
}
