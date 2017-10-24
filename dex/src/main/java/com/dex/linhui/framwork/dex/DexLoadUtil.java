package com.dex.linhui.framwork.dex;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Environment;

import com.lin.alllib.AppLife;
import com.lin.alllib.framwork.manager.ActivityManager;

import java.io.File;

import dalvik.system.DexClassLoader;


/**
 * Created by linhui on 2017/10/17.
 */
public class DexLoadUtil {

    private static final String TAG = "loadDex1";
    public static File PARENT_FILE = new File(Environment.getExternalStorageDirectory() + File.separator + "LuPingDaShi");

    public static final String DEFUALT_APP_APK_NAME = "app-release.apk";
    public static final String DEFUALT_HZC_APK_NAME = "hzc-release.apk";

    public static final String DEX_KEY_APP = "App";
    public static final String DEX_KEY_HZC = "hzc";

    private static Object load(File file, String dexclass) {
        if (file.isFile() && file.exists()) {
            long longs = file.length();

            DexClassLoader dexClassLoader =
                    new DexClassLoader(
                            file.getAbsolutePath(),
                            AppLife.getInstance().getApplication().getDir("dex", Context.MODE_PRIVATE).getAbsolutePath(),
                            null, AppLife.getInstance().getApplication().getClassLoader());
            Class libProviderClazz = null;
            try {
                libProviderClazz = dexClassLoader.loadClass(dexclass);
                return libProviderClazz.newInstance();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    public static Object loadDex(String apk,String dexclass) {
        switch (apk) {
            case DEX_KEY_APP:
                return load(new File(PARENT_FILE, DEFUALT_APP_APK_NAME), dexclass);
            case DEX_KEY_HZC:
                return load(new File(PARENT_FILE, DEFUALT_HZC_APK_NAME), dexclass);
            default:
                return null;
        }

    }

}
