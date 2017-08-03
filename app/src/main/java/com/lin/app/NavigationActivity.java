package com.lin.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.NavigationModel;

public class NavigationActivity extends WoodActivity {

    @Override
    protected Model configurationModel() {
       return new NavigationModel();
    }
}
