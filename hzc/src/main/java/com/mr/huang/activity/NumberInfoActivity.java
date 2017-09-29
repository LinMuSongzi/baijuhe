package com.mr.huang.activity;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

import com.mr.huang.BaseActivity;
import com.mr.huang.R;

import butterknife.Bind;

/**
 * Created by linhui on 2017/9/29.
 */
public class NumberInfoActivity extends BaseActivity {

    @Bind(R.id.id_number_info_recyclerView)
    RecyclerView id_number_info_recyclerView;


    @Override
    public void init() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_numberinfo;
    }
}
