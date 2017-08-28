package lin.lpds.com.dextest;

import android.app.Application;

import lin.lpds.com.dextest.note.ExceptioPen;
import lin.lpds.com.dextest.note.ExceptionNoteInfo;
import lin.lpds.com.dextest.note.NoteInfo;

/**
 * Created by linhui on 2017/8/28.
 */
public class Apk extends Application {
    @Override
    public void onCreate() {
        super.onCreate();



        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {

//                ExceptioPen.getExceptioNote().write(new NoteInfo(throwable.toString()));

                ExceptioPen.getExceptioNote().write(new ExceptionNoteInfo(throwable));
            }
        });
    }
}
