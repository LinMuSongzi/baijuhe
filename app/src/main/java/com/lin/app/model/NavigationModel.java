package com.lin.app.model;

import android.os.Bundle;
import android.widget.TextView;

import com.lin.alllib.Model;
import com.lin.alllib.framwork.manager.ActivityManager;
import com.lin.app.R;

/**
 * Created by lpds on 2017/7/26.
 */
public class NavigationModel extends Model {

    TextView id_content_tv;

    @Override
    protected int getContentView() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        id_content_tv = (TextView) findViewById(R.id.id_content_tv);
        id_content_tv.setText("hello");

    }

}
