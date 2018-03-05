package com.l.ipc.activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.l.ipc.ICalculateInterface;
import com.l.ipc.IWarehouseInterface;
import com.l.ipc.R;
import com.l.ipc.imp.Book;
import com.l.ipc.imp.PlanService;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {


    private EditText id_top_edittext;
    private EditText id_bottom_edittext;
    private TextView sum_tv;
    private AtomicBoolean calculateBoolean = new AtomicBoolean(false);
    private AtomicBoolean entityBoolean = new AtomicBoolean(false);
    private ICalculateInterface calculateInterface;
    private IWarehouseInterface warehouseInterface;
    ServiceConnection calculateInterfaceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service != null) {
                calculateInterface = ICalculateInterface.Stub.asInterface(service);
                handler.sendEmptyMessage(200);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    ServiceConnection warehouseInterfaceConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service != null) {
                warehouseInterface = IWarehouseInterface.Stub.asInterface(service);
                handler.sendEmptyMessage(201);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    bind();
                    break;
                case 101:
                    bind2();
                    break;
                case 200:
                    calculateBoolean.set(true);
                    Toast.makeText(MainActivity.this, "初始化计算成功...", Toast.LENGTH_SHORT).show();
                    break;
                case 201:
                    entityBoolean.set(true);
                    Toast.makeText(MainActivity.this, "初始化entity成功...", Toast.LENGTH_SHORT).show();
                    break;
                case 404:
                    if (calculateBoolean.get()) {
                        unbindService(calculateInterfaceConnection);
                        calculateBoolean.set(false);
                    }
                    break;
                case 401:
                    if (entityBoolean.get()) {
                        unbindService(warehouseInterfaceConnection2);
                        entityBoolean.set(false);
                    }
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id_top_edittext = (EditText) findViewById(R.id.id_top_edittext);
        id_bottom_edittext = (EditText) findViewById(R.id.id_bottom_edittext);
        sum_tv = (TextView) findViewById(R.id.sum_tv);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        bind2();
    }

    private void startBind() {
        if (entityBoolean.get()) {
            handler.sendEmptyMessage(401);
        }
        if (!calculateBoolean.get()) {
            handler.sendEmptyMessage(100);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.sendEmptyMessage(404);
        handler.sendEmptyMessage(401);
    }

    public void sumOnClick(View view) throws RemoteException {
        if (!calculateBoolean.get()) {
            Toast.makeText(this, "初始化中.....", Toast.LENGTH_SHORT).show();
            startBind();
            return;
        }
        if (id_bottom_edittext.getText().toString().isEmpty()
                || id_top_edittext.toString().isEmpty()) {
            Toast.makeText(this, "请认真确认计算数值！", Toast.LENGTH_SHORT).show();
            return;
        }
        String end = null;
        switch ((String) view.getTag()) {
            case "+":
                end = String.valueOf(calculateInterface.adding(Double.parseDouble(id_top_edittext.getText().toString())
                        , Double.parseDouble(id_bottom_edittext.getText().toString())));
                break;
            case "-":
                end = String.valueOf(calculateInterface.subtract(Double.parseDouble(id_top_edittext.getText().toString())
                        , Double.parseDouble(id_bottom_edittext.getText().toString())));
                break;
            case "*":
                end = String.valueOf(calculateInterface.multiply(Double.parseDouble(id_top_edittext.getText().toString())
                        , Double.parseDouble(id_bottom_edittext.getText().toString())));
                break;
            case "/":
                end = String.valueOf(calculateInterface.divide(Double.parseDouble(id_top_edittext.getText().toString())
                        , Double.parseDouble(id_bottom_edittext.getText().toString())));
                break;
        }
        sum_tv.setText(end);
    }


    static final String[] DEFUALT_FIRSTER_NAME = {"Jay", "Uox", "Chen", "Lebron", "Kobe", "Tim", "Ted"};
    static final String[] DEFUALT_LAST_NAME = {"Brown", "Noxce", "HwCust", "Lebron", "plan", "HaryketiHer", "Ben"};

    public void saveBookClick(View view) throws RemoteException {

        if (calculateBoolean.get() || !entityBoolean.get()) {
            Toast.makeText(this, "初始化中.....", Toast.LENGTH_SHORT).show();
            start2Bind();
            return;
        }

        switch ((String) view.getTag()) {
            case "saveBook":
                Random random = new Random();
                random.nextInt(DEFUALT_FIRSTER_NAME.length);
                Book book = new Book();
                book.setName(DEFUALT_FIRSTER_NAME[random.nextInt(DEFUALT_FIRSTER_NAME.length)] + " " +
                        DEFUALT_LAST_NAME[random.nextInt(DEFUALT_LAST_NAME.length)]);
                book.setPrice(random.nextInt(1000));
                warehouseInterface.set(book);
                break;
            case "getBook":
                final Object o = warehouseInterface.getEntity(0);
                ((TextView) view).setText("get book : " + o);
                break;
        }

    }

    private void start2Bind() {
        if (calculateBoolean.get()) {
            handler.sendEmptyMessage(404);
        }
        if (!entityBoolean.get()) {
            handler.sendEmptyMessage(101);
        }
    }

    private void bind2() {
        Intent intent = new Intent(this, PlanService.class);
        intent.putExtra("action", "IWarehouseInterface");
        bindService(intent, warehouseInterfaceConnection2, Context.BIND_AUTO_CREATE);
    }

    private void bind() {
        Intent intent = new Intent(this, PlanService.class);
        intent.putExtra("action", "ICalculateInterface");
        bindService(intent, calculateInterfaceConnection, Context.BIND_AUTO_CREATE);
    }

}
