package com.mr.huang.common;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.mr.huang.framwork.AppManager;

/**
 * Created by linhui on 2017/9/30.
 */
public class ShowTip {




    public static void showToast(String msg){


        Toast.makeText(AppManager.getInstance().getCurrentActivity(),msg,Toast.LENGTH_SHORT).show();

    }

    public static void showSnackbar(View v, String msg){


//        Toast.makeText(AppManager.getInstance().getCurrentActivity(),msg,Toast.LENGTH_SHORT).show();
        Snackbar.make(v,msg,1500).show();

    }


}
