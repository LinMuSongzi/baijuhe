package com.lin.app.activity;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.SelectInfoModel;
import com.lin.app.model.TransformModel;
import com.lin.app.model.VideoDemoModel;

/**
 * Created by linhui on 2017/12/6.
 */
public class TowActivity extends WoodActivity {

    Model selectInfoModel = new TransformModel();

    @Override
    protected Model<?> configurationModel() {

        return selectInfoModel;
    }
}
