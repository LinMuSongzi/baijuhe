package lin.lpds.com.dextest;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import lin.lpds.com.dextest.note.ExceptioPen;
import lin.lpds.com.dextest.note.NoteInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExceptioPen.getExceptioNote().write(new NoteInfo("onCreate"));
        if (Build.VERSION.SDK_INT > 22) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x09);
        }
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
        super.onDestroy();
        ExceptioPen.getExceptioNote().write(new NoteInfo("onDestroy"));
    }

//    public void show(View v){

//        ExceptioPen.getExceptioNote().readAll();

//    }

}
