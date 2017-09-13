package com.pathtest.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.pathtest.R;
import com.pathtest.support.IPathModel;
import com.pathtest.support.PathModel;

public class ScrollingActivity extends WoodActivity {

    IPathModel iPathModel = new PathModel();

    @Override
    protected Model configurationModel() {
        return (Model) iPathModel;
    }
}
