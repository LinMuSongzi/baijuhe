package com.lin.app.activity;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.MainModel;

/**
 * Created by linhui on 2017/8/3.
 */
public class MainActivity extends WoodActivity{
    @Override
    protected Model configurationModel() {
        return new MainModel();
    }
}
