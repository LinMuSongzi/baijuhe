package com.lin.app.ui.activity;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.NavigationModel;

public class NavigationActivity extends WoodActivity {

    private NavigationModel model = new NavigationModel();

    @Override
    protected Model configurationModel() {
       return model;
    }
}
