package y.com.sqlitesdk.framework.entity.respone;

import y.com.sqlitesdk.framework.entity.Stickness;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by lpds on 2017/4/17.
 */
public class ModifyRespone<T extends IModel> extends Stickness {
    private long modiId;
    private T model;

    public ModifyRespone(long modiId, T model) {
        this.modiId = modiId;
        this.model = model;
    }

    public long getModiId() {
        return modiId;
    }

    public void setDeleteId(long modiId) {
        this.modiId = modiId;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
