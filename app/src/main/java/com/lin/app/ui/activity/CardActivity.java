package com.lin.app.ui.activity;

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
}
