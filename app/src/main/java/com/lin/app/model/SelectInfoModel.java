package com.lin.app.model;

import android.os.Bundle;
import android.util.Log;

import com.lin.alllib.Model;
import com.lin.alllib.framwork.RequestManager;
import com.lin.app.R;
import com.lin.app.common.SubscriberImp;
import com.lin.app.data.respone.InitEntity;
import com.lin.app.request.ApiImp;
import com.lin.app.request.LpdsImp;
import com.lin.app.request.retrofit.LpdsApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hui on 2017/9/16.
 */

public class SelectInfoModel extends Model<SelectInfoModel> {

    @Override
    protected int getContentView() {
        return R.layout.activity_select_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public SelectInfoModel getAffirmObject() {
        return null;
    }





}
