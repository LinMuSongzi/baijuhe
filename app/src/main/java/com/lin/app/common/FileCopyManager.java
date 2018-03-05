package com.lin.app.common;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linhui on 2018/2/24.
 */
public class FileCopyManager {

    private final Map<String, Object> para = new HashMap<>();


    public void startCopy(final File starFile, final File endFile, Context context){

        if (!starFile.isFile()) {
            return;
        }

        final Runnable r  = new Runnable(){
            @Override
            public void run() {
                try {
                    long maxleng = starFile.length();
                    FileOutputStream fileOutputStream = new FileOutputStream(endFile);
                    FileInputStream fileInputStream = new FileInputStream(starFile);
                    int leng = 0;
                    byte[] bytes = new byte[1024 * 5];
                    leng = fileInputStream.read(bytes);
                    long max = 0;
                    while (leng != -1) {
                        fileOutputStream.write(bytes, 0, leng);
                        max += leng = fileInputStream.read(bytes);
                        Log.i("startCopy", String.format("file max leng = %.2f: this leng = %d", (maxleng / 1024 / 1024f), max));
                    }
                    fileInputStream.close();
                    fileOutputStream.close();
                }catch (Exception ex){
                    ex.printStackTrace();

//                    Log.i("startCopy", "run: "+);
                }
            }
        };
        new Thread(r).start();


    }

}
