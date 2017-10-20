package com.dex.linhui.ui.activity;


import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;

import com.dex.linhui.ui.model.NavigationModelImp;
import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NavigationActivity extends WoodActivity<Object,NavigationModelImp> {

    @Override
    protected NavigationModelImp configurationModel() {
        return new NavigationModelImp();
    }


    public interface INavigationModel{

        void onDex();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100 && PackageManager.PERMISSION_GRANTED == grantResults[0]){

            getModel().getAffirmObject().onDex();


        }
    }
}
