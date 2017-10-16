package com.mr.huang.common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by linhui on 2017/10/16.
 */
public class DexLoad {

    private static final String TAG = "loadDex1";
    private static File PARENT_FILE = new File(Environment.getExternalStorageDirectory() + File.separator + "LuPingDaShi");


    public static Object loadDex1(Context context){
        File file = new File(PARENT_FILE, "app-release.apk");
        if (file.isFile() && file.exists()) {

            long longs = file.length();

            DexClassLoader dexClassLoader =
                    new DexClassLoader(
                            file.getAbsolutePath(),
                            context.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(),
                            null, context.getClassLoader());
            Class libProviderClazz = null;
            try {
                libProviderClazz = dexClassLoader.loadClass("com.lin.app.common.CalculateImp");
                return libProviderClazz.newInstance();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


}
