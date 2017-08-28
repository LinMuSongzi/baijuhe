package lin.lpds.com.dextest.note;

import android.os.Environment;

import java.io.File;

/**
 * Created by linhui on 2017/8/28.
 */
public interface IPen {
    File FOLDER = new File(Environment.getExternalStorageDirectory(), "lpds_note");

    int CACHE_MAX_DAY = 7;

    void write(NoteInfo noteEntity);
    @Deprecated
    void readAll();
}
