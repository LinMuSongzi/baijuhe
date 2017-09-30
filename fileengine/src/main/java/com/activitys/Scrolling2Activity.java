package com.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fileengine.R;
import com.fileengine.commander.Engine;
import com.fileengine.commander.IEngine;
import com.fileengine.commander.OnExecuteListener;
import com.fileengine.commander.engine2.Engine2;
import com.fileengine.commander.entity.EngineEntity;
import com.fileengine.commander.entity.IFileEntity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Scrolling2Activity extends AppCompatActivity {

    public static Activity ACTIVITY;

    @Bind(R.id.id_text)
    TextView id_text;
    @Bind(R.id.id_text_2)
    TextView id_text_2;
    @Bind(R.id.id_text_speed)
    TextView id_text_speed;
    @Bind(R.id.id_spinner_postfix)
    Spinner id_spinner_postfix;
    private Toast toast = null;
    Engine2 iEngine;
    private long time = 0;

    @OnClick(R.id.id_go)
    public void go() {
        time = 0;

        EngineEntity engineEntity = new EngineEntity();
        if (iEngine == null) {
            iEngine = new Engine2();
        }
        iEngine.stop();
        engineEntity.setFile(Environment.getExternalStorageDirectory());
        if (id_text_speed.getText().toString().isEmpty()) {
            engineEntity.setSpeed(1);
        } else {
            engineEntity.setSpeed(Integer.parseInt(id_text_speed.getText().toString()));
        }
        engineEntity.setPostfix(chooseString);
        iEngine.prepare(engineEntity, new OnExecuteListener<IFileEntity>() {

            @Override
            public void onStart() {
                time = System.currentTimeMillis();
                id_text.setText("开始：线程数" + id_text_speed.getText().toString() + "   " + new SimpleDateFormat("HH:mm:ss:SSS").format(time));

            }

            @Override
            public void onNext(IFileEntity collection, int leng) {

                final String txt = "用时: " + new SimpleDateFormat("mm:ss:SSS").format(System.currentTimeMillis() - time) +
                        " \n 文件：<font color=#ff0000>" + leng + "</font>\n线程：<font color=#ffa40a>" + Thread.currentThread().getName() + "</font> \n 当前文件：" + collection.getName();
                Log.i("onNext", txt);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        id_text_2.setText(Html.fromHtml(txt));
                    }
                });

            }
        });
        iEngine.startScanner();
    }

    private void show(String msg) {

        toast.setText(msg);
        toast.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ACTIVITY = this;
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initList();

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        id_spinner_postfix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chooseString = CHOOSE_STRING.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private void initList() {

        CHOOSE_STRING.add(null);
        CHOOSE_STRING.add(new String[]{".txt"});
        CHOOSE_STRING.add(new String[]{".3gp", ".mp4", ".rmvb", ".avi", ".fly"});
        CHOOSE_STRING.add(new String[]{".jpg", ".jpeg"});
        CHOOSE_STRING.add(new String[]{".html", ".xml", ".json"});

    }

    public String[] chooseString;

    @Override
    protected void onDestroy() {
        ACTIVITY = null;
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public static final List<String[]> CHOOSE_STRING = new ArrayList<>();

}
