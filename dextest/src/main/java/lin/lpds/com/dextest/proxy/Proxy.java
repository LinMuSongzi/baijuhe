package lin.lpds.com.dextest.proxy;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Created by linhui on 2017/8/16.
 */
public class Proxy {
    private static Proxy ourInstance = new Proxy();
    private DexClassLoader dexClassLoader;
    private Context context;
    private File cacheDic;
    private File filesApk = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+"hello","hello.apk");

    private Proxy() {
    }

    public static Proxy getInstance() {
        return ourInstance;
    }

    public void loader(){


        //创建一个意图，用来找到指定的apk
        Intent intent = new Intent("lin.lpds.com.dextest");
        //获得包管理器
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveinfoes =  pm.queryIntentActivities(intent, 0);
        if(resolveinfoes.size()==0){
            return;
        }
        //获得指定的activity的信息
        ActivityInfo actInfo = resolveinfoes.get(0).activityInfo;

        //获得包名
        String packageName = actInfo.packageName;
        //获得apk的目录或者jar的目录
        String apkPath = actInfo.applicationInfo.sourceDir;
        //dex解压后的目录,注意，这个用宿主程序的目录，android中只允许程序读取写自己
        //目录下的文件
        String dexOutputDir = context.getApplicationInfo().dataDir;

        //native代码的目录
        String libPath = actInfo.applicationInfo.nativeLibraryDir;

        dexClassLoader = new DexClassLoader(filesApk.getAbsolutePath(),
                dexOutputDir,
                libPath,
                getClass().getClassLoader());

        try {
            Class<?> clazz = dexClassLoader.loadClass(packageName+".proxy.Hello");

            Object obj = clazz.newInstance();

            Method method = clazz.getMethod("hello", null);

            Integer ret = (Integer)method.invoke(obj);

            Log.d("JG", "返回的调用结果为:" + ret);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
