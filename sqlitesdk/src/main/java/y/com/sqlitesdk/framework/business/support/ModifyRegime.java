package y.com.sqlitesdk.framework.business.support;

import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/9/2.
 */
public class ModifyRegime<T extends IModel<T>> extends Regime<T> {
    protected ModifyRegime(T t) {
        super(t);
    }

    @Override
    public String buildSql() {
        return null;
    }
}
