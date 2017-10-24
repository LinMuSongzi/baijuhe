package com.lin.app.model;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.lin.alllib.Model;
import com.lin.app.R;

/**
 * Created by linhui on 2017/8/10.
 */
public class NavigationModel extends Model {

    private Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_navigatiom;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        toolbar = getToolbar();

    }

    @Override
    public Object getAffirmObject() {
        return null;
    }
}
