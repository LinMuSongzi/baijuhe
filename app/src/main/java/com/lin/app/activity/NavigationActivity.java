package com.lin.app.activity;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.activity.fragment.NavigationFragment;
import com.lin.app.model.MainModel;
import com.lin.app.model.NavigationModel;

public class NavigationActivity extends WoodActivity {

    private NavigationModel model = new NavigationModel();

    @Override
    protected Model configurationModel() {
       return model;
    }
}
