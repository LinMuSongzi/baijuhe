package com.lin.app.model.support;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Hui on 2017/9/16.
 */

public interface IActivityImpl {

    AppCompatActivity getActivity();

    void close();
}
