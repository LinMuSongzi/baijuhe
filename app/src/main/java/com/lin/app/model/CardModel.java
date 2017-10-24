package com.lin.app.model;

import android.os.Bundle;
import android.view.View;

import com.lin.alllib.Model;
import com.lin.app.R;

/**
 * Created by linhui on 2017/8/10.
 */
public class CardModel extends Model {
    @Override
    protected int getContentView() {
        return R.layout.activity_card;
    }

    @Override
    protected void onCreateBefore() {
        super.onCreateBefore();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public Object getAffirmObject() {
        return null;
    }
}
