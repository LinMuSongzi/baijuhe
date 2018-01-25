package com.l.ipc.imp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.l.ipc.ICalculateInterface;
import com.l.ipc.IWarehouseInterface;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by linhui on 2018/1/25.
 */
public class PlanService extends Service {
    private static final String TAG = "CalculateService";


    private static Queue<Book> BOOKS = new LinkedList<Book>();

    private static final class CalculateInterfaceImp extends ICalculateInterface.Stub {
        @Override
        public double adding(double one, double two) throws RemoteException {
            Log.i(TAG, "adding: ");
            return one + two;
        }

        @Override
        public double multiply(double one, double two) throws RemoteException {
            Log.i(TAG, "multiply: ");
            return one * two;
        }

        @Override
        public double subtract(double one, double two) throws RemoteException {
            Log.i(TAG, "subtract: ");
            return one - two;
        }

        @Override
        public double divide(double one, double two) throws RemoteException {
            Log.i(TAG, "divide: ");
            return one / two;
        }

        @Override
        public String getServiceName() throws RemoteException {
            return "CalculateService";
        }
    };

    private static final class WarehouseInterfaceImp extends IWarehouseInterface.Stub {

        @Override
        public void set(Book book) throws RemoteException {
            BOOKS.add(book);
        }

        @Override
        public Book getEntity(int i) throws RemoteException {
            if(BOOKS.size() == 0){
                return null;
            }
            if (i > 0) {
                return ((List<Book>) BOOKS).get(i);
            } else {
                return BOOKS.poll();
            }
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        switch (intent.getStringExtra("action")) {
            case "IWarehouseInterface":
                return new WarehouseInterfaceImp();
            case "ICalculateInterface":
                return new CalculateInterfaceImp();
        }
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }
}
