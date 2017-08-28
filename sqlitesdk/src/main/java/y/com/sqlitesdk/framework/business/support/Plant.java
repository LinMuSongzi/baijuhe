package y.com.sqlitesdk.framework.business.support;

import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/8/25.
 */
public interface Plant extends Execute{
    int MODIFY = 0x99a;
    int INSTER = 0x99b;
    int DELETE = 0x99c;
    int SELECT = 0x99d;

    Plant select(Child child);
    Plant wheres(Child child);

    final class Child{
        String key;
        String values;

        public Child(String key, String values) {
            this.key = key;
            this.values = values;
        }
    }




}
