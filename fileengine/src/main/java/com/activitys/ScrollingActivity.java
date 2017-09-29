package com.activitys;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fileengine.R;
import com.fileengine.commander.Engine;
import com.fileengine.commander.IEngine;
import com.fileengine.commander.OnExecuteListener;
import com.fileengine.commander.entity.IFileEntity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScrollingActivity extends AppCompatActivity {

    @Bind(R.id.id_text)
    TextView id_text;
    @Bind(R.id.id_text_2)
    TextView id_text_2;
    @Bind(R.id.id_text_speed)
    TextView id_text_speed;
    @Bind(R.id.id_spinner_postfix)
    Spinner id_spinner_postfix;
//    @Bind(R.id.id_go)
//    TextView id_go;

    IEngine iEngine;
    private int i = 0;
    private long time = 0;
    @OnClick(R.id.id_go)
    public void go() {
        i = 0;
        time = 0;
        if(iEngine == null) {
            iEngine = new Engine();
        }
        if (id_text_speed.getText().toString().isEmpty()) {
            iEngine.setSpeed(10);
        } else {
            iEngine.setSpeed(Integer.parseInt(id_text_speed.getText().toString()));
        }
        iEngine.prepare();
        iEngine.startScanner(new IEngine.ScannerFileConfig() {
            @Override
            public File getRootFile() {
                return Environment.getExternalStorageDirectory();
            }

            @Override
            public String[] getPostfix() {
                return chooseString;
            }
        }, new OnExecuteListener() {
            @Override
            public void onSucceed() {
            }

            @Override
            public void onError(Exception ex) {

            }

            @Override
            public void onNext(IFileEntity collection) {
                if (collection == null) {
                    time = System.currentTimeMillis();
                    id_text.setText("开始：线程数" +id_text_speed.getText().toString()+"   "+ new SimpleDateFormat("HH:mm:ss:SSS").format(time));
                } else {
                    final String txt = "用时" + new SimpleDateFormat("ss:SSS").format(System.currentTimeMillis() - time) + "找到" + (++i) + "个文件";
                    Log.i("onNext", txt);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            id_text_2.setText(txt);
                        }
                    });
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        CHOOSE_STRING.add(new String[]{".3gp", ".mp4", "rmvb", "avi", ".fly"});
        CHOOSE_STRING.add(new String[]{".jpg", ".jpeg", ".png", ".gif"});
        CHOOSE_STRING.add(new String[]{".html", ".xml", ".json"});

    }

    public String[] chooseString;

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public static final List<String[]> CHOOSE_STRING = new ArrayList<>();

}
