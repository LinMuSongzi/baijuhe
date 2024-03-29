package y.com.sqlitesdk.framework.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import y.com.sqlitesdk.framework.IfeimoSqliteSdk;
import y.com.sqlitesdk.framework.Mode;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by lpds on 2017/4/10.
 */
public final class Access{
    private static Access access;
    private Handler handler;

    static{
        access = new Access();
    }

    private final String TAG = "ifeimo_sqlite_Access";
    private SQLiteDatabase sqLiteDatabase;
    private int mode = Mode.KEEP_LIVE;
    private List<String> triggers;

    private Access(){
        HandlerThread handlerThread = new HandlerThread("Access_sqlite");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        triggers = new ArrayList<>();
    }

    public static void runCustomThread(Execute execute){
        access.execute(execute);
    }

    public static void run(Execute execute){
        access.execute2(execute);
    }

    public static void setSqliteDB(SQLiteDatabase sqliteDB){
        access.setSqLiteDatabase(sqliteDB);
    }

    public static List<String> getAlltriggers(){
        return access.triggers;
    }

    public static SQLiteDatabase get(){
            return access.getSqLiteDatabase();
    }

    private void switchMode(int mode) {
        if(sqLiteDatabase != null && sqLiteDatabase.isOpen()){
            return ;
        }
        switch (mode){
            case Mode.AUTO_LIVE:
                break;
            case Mode.KEEP_LIVE:
                sqLiteDatabase = SdkConfig.getInstances().getDefaultWriter();
                sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
                break;
        }
    }

    @Deprecated
    public void init(){
        access.switchMode(Mode.KEEP_LIVE);
    }

    private void getAllTriggers(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master\n" +
                " WHERE type = 'trigger'",null);
        if(cursor.moveToFirst()) {
            do {
                triggers.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
    }

    /**
     * 无轮询执行
     * @param execute
     */
    public void execute(Execute execute){
        synchronized (this) {
            try {
                if (execute == null) {
                    return;
                }
                if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                    throw new Exception("不允许在主线程调用此方法");
                }
                if(sqLiteDatabase == null){

                    Log.i(TAG, "execute: "+sqLiteDatabase);

                }
                sqLiteDatabase.beginTransaction();
                execute.onExecute(sqLiteDatabase);
                sqLiteDatabase.setTransactionSuccessful();
            } catch (Exception ex) {
                execute.onExternalError();
                ex.printStackTrace();
            } finally {
                sqLiteDatabase.endTransaction();
            }
        }
    }

    private void post(Runnable runnable){
        while (true){
            if(handler != null){
                break;
            }
        }
        handler.post(runnable);
    }

    /**
     * 内部轮询
     * @param execute
     */
    public void execute2(final Execute execute){
        post(new Runnable() {
            @Override
            public void run() {
                execute(execute);
            }
        });
    }

    public SQLiteDatabase getSqLiteDatabase() {
        synchronized (this){
            if (sqLiteDatabase == null) {
                if(IfeimoSqliteSdk.IPMAIN != null){
                    sqLiteDatabase = IfeimoSqliteSdk.IPMAIN.getSQLiteDatabase();
                }
            }
            return sqLiteDatabase;
        }
    }

    public void setSqLiteDatabase(SQLiteDatabase sqLiteDatabase){
        synchronized (this){
            if (this.sqLiteDatabase != null && this.sqLiteDatabase.isOpen()) {
                return;
            }
            this.sqLiteDatabase = sqLiteDatabase;
            this.sqLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
            getAllTriggers();
        }
    }
}
