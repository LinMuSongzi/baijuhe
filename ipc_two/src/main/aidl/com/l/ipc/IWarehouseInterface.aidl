// IWarehouseInterface.aidl
package com.l.ipc;
// Declare any non-default types here with import statements
import com.l.ipc.imp.Book;
interface IWarehouseInterface {
    void set(in Book book);
    Book getEntity(int i);
}
