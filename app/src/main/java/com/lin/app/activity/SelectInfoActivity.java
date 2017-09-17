package com.lin.app.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.support.IActivityImpl;
import com.lin.app.model.support.SupportSelectInfo;

/**
 * Created by Hui on 2017/9/16.
 */

public class SelectInfoActivity extends AppCompatActivity implements IActivityImpl {

    private SupportSelectInfo supportSelectInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        supportSelectInfo = new SupportSelectInfo(this);
        setContentView(supportSelectInfo.getContentView());
        supportSelectInfo.init();
        supportSelectInfo.loadData();
    }

    @Override
    public AppCompatActivity getAtivity() {
        return this;
    }
}
