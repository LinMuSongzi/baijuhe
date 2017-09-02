package lin.lpds.com.dextest;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.icu.util.ULocale;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import lin.lpds.com.dextest.note.ExceptioPen;
import lin.lpds.com.dextest.note.NoteInfo;

public class MainActivity extends AppCompatActivity implements Handler.Callback,ServiceConnection{
    private Messenger mService;
    private Messenger mClient = new Messenger(new Handler(this));



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExceptioPen.getExceptioNote().write(new NoteInfo("onCreate"));
        if (Build.VERSION.SDK_INT > 22) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x09);
        }
        bind();

    }

    public static Intent getExplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }

    private void bind() {

        Intent intent = new Intent();
        intent.setAction("com.lin.app.postman");
        bindService(getExplicitIntent(this, intent), this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ExceptioPen.getExceptioNote().write(new NoteInfo("onRestart"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        ExceptioPen.getExceptioNote().write(new NoteInfo("onResume"));
    }


    @Override
    protected void onStart() {
        super.onStart();
        ExceptioPen.getExceptioNote().write(new NoteInfo("onStart"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExceptioPen.getExceptioNote().write(new NoteInfo("onPause"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        ExceptioPen.getExceptioNote().write(new NoteInfo("onStop"));
    }

    @Override
    protected void onDestroy() {
        unbindService(this);
        super.onDestroy();
        ExceptioPen.getExceptioNote().write(new NoteInfo("onDestroy"));
    }


    @Override
    public boolean handleMessage(Message msg) {

        Toast.makeText(this,msg.arg1 + " + " + msg.arg2 + " = " + ((Bundle)msg.obj).getInt("sum"),Toast.LENGTH_SHORT).show();


        return true;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mService = new Messenger(iBinder);
        Message message = Message.obtain();
        message.what = 0x9123;
        message.arg2 = (int) (Math.random() * 100);
        message.arg1 = (int) (Math.random() * 100);
        message.replyTo = mClient;
        try {
            mService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public void show(View v){

//        ExceptioPen.getExceptioNote().readAll();

        if (mService != null) {
            Message message = Message.obtain();
            message.what = 0x9123;
            message.arg2 = (int) (Math.random() * 100);
            message.arg1 = (int) (Math.random() * 100);
            message.replyTo = mClient;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                mService = null;
                e.printStackTrace();
            }
        }else{
            bind();
        }

    }

}
