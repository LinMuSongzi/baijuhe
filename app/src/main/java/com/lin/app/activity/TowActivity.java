package com.lin.app.activity;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.SelectInfoModel;

/**
 * Created by linhui on 2017/12/6.
 */
public class TowActivity extends WoodActivity {

    SelectInfoModel selectInfoModel = new SelectInfoModel();

    @Override
    protected Model<?> configurationModel() {
        return selectInfoModel;
    }
}
