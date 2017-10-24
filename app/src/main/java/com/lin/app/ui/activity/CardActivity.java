package com.lin.app.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.lin.alllib.Model;
import com.lin.alllib.WoodActivity;
import com.lin.app.model.CardModel;

/**
 * Created by linhui on 2017/8/10.
 */
public class CardActivity extends WoodActivity {
    @Override
    protected Model configurationModel() {
        return new CardModel();
    }

    private void test(){

        if(Build.VERSION.SDK_INT > 22){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},0x100);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
