package lin.lpds.com.dextest.note;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linhui on 2017/8/24.
 */
public class ExceptioPen implements IPen {


    private Handler handler;
    public File FILE;
    public static final ExceptioPen EXCEPTIO_NOTE = new ExceptioPen();

    private ExceptioPen() {
        create();
    }

    public static IPen getExceptioNote() {
        return EXCEPTIO_NOTE;
    }

    private boolean check() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    private void create() {
        checkCacheFile();
        if (!check()) {

            return;
        }

        HandlerThread handlerThread = new HandlerThread("ExceptioPen");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());


        if (!FOLDER.exists()) {
            FOLDER.mkdirs();
        }
        FILE = new File(FOLDER, new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".txt");
        if (!FILE.exists()) {
            try {
                FILE.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void write(final NoteInfo noteEntity) {
        if (!check()) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                String json = new Gson().toJson(noteEntity) + "\n";
                try {
//                    json = DesUtil.encrypt(json);
                    FileOutputStream fileOutputStream = new FileOutputStream(FILE, true);
                    fileOutputStream.write(json.getBytes());
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void readAll() {
        if (!check()) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (FILE.length() == 0) {
                    return;
                }
                try {
                    BufferedReader inputStream = new BufferedReader(new FileReader(FILE));
                    String str = null;
                    StringBuilder stringBuilder = new StringBuilder();
                    if (inputStream.ready()) {
                        do {
                            if (null != str) {
                                stringBuilder.append(str);
                            }
                            str = inputStream.readLine();
                        } while (str != null);
                        inputStream.close();
                    }
//                    Log.i("ExceptioPen", "run: " + result);
//                    final String result = DesUtil.decrypt(stringBuilder.toString());
                    Log.i("ExceptioPen", "run: " + stringBuilder.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void checkCacheFile() {

        if (FOLDER.exists()) {
            try {

                File[] files = FOLDER.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {

                        int l;
                        try {
                            l = Integer.parseInt(file.getName());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            return true;
                        }

                        int n = Integer.parseInt(FILE.getName());

                        if (n - l > CACHE_MAX_DAY) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });

                for (File file : files) {
                    if (file.exists()) {
                        file.delete();
                        write(new NoteInfo("delete file " + file.getName()));
                    }
                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }

    }

}
