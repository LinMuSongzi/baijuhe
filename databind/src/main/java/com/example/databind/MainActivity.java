package com.example.databind;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        ViewDataBinding activityBasicBinding =
                DataBindingUtil.setContentView(this,R.layout.activity_main);
//        activityBasicBinding.setVariable()
    }
}
