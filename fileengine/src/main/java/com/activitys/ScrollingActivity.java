package com.activitys;

import android.os.Bundle;
import android.os.Environment;
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
import com.fileengine.commander.Engine3;
import com.fileengine.commander.IEngine;
import com.fileengine.commander.OnExecuteListener;
import com.fileengine.commander.entity.DocBean;
import com.fileengine.commander.entity.EngineEntity;
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
    private Toast toast = null;
    IEngine iEngine;
    private long time = 0;
    EngineEntity engineEntityConfig;

    @OnClick(R.id.id_go)
    public void go() {
        engineEntityConfig = new EngineEntity();
//        new Thread(){
//            @Override
//            public void run() {
//                new Engine3().queryFiles(docBeanOnExecuteListener,engineEntityConfig);
//            }
//        }.start();

        time = 0;
        if (iEngine == null) {
            iEngine = new Engine();
            iEngine.init(new OnExecuteListener<IFileEntity>() {

                @Override
                public void onStart() {
                    time = System.currentTimeMillis();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            id_text.setText(Html.fromHtml("开始：线程数 <font color=#89ac9f>" + engineEntityConfig.getSpeed()
                                    + "</font>  " + new SimpleDateFormat("HH:mm:ss:SSS").format(time)));

                        }
                    });
                }

                @Override
                public void onNext(IFileEntity collection, int count) {
                    final String txt = "用时" + new SimpleDateFormat("mm:ss:SSS").format(System.currentTimeMillis() - time) + "  " +
                            " 找到 <font color=#ff0000>" + count + "</font>个文件 " + collection.getFilePath();
                    Log.i("onNext", txt);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            id_text_2.setText(Html.fromHtml(txt));
                        }
                    });

                }
            });
        }
        engineEntityConfig = new EngineEntity();

        engineEntityConfig.setPostfix(chooseString);
        if (id_text_speed.getText().toString().isEmpty()) {
            engineEntityConfig.setSpeed(1);
        } else {
            engineEntityConfig.setSpeed(Integer.parseInt(id_text_speed.getText().toString()));
        }
        iEngine.prepare(engineEntityConfig);
        iEngine.startQueryFile();
    }

    OnExecuteListener<DocBean> docBeanOnExecuteListener = new OnExecuteListener<DocBean>() {

        @Override
        public void onStart() {
            time = System.currentTimeMillis();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    id_text.setText(Html.fromHtml("开始：线程数 <font color=#89ac9f>" + engineEntityConfig.getSpeed()
                            + "</font>  " + new SimpleDateFormat("HH:mm:ss:SSS").format(time)));

                }
            });
        }

        @Override
        public void onNext(DocBean collection, int count) {
            final String txt = "用时" + new SimpleDateFormat("mm:ss:SSS").format(System.currentTimeMillis() - time) + "  " +
                    " 找到 <font color=#ff0000>" + count + "</font>个文件 " + collection.getPath();
            Log.i("onNext", txt);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    id_text_2.setText(Html.fromHtml(txt));
                }
            });

        }
    };

    @OnClick(R.id.id_stop)
    public void id_stop(View v) {
        iEngine.stop();
    }


    private void show(String msg) {

        toast.setText(msg);
        toast.show();

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
        CHOOSE_STRING.add(new String[]{".3gp", ".mp4", "rmvb", "avi", ".fly"});
        CHOOSE_STRING.add(new String[]{".jpg", ".jpeg"});
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
