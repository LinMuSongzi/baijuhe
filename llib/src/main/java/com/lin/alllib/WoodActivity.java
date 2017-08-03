package com.lin.alllib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * Created by lpds on 2017/7/26.
 */
public abstract class WoodActivity extends AppCompatActivity{

    protected final String TAG = getClass().getSimpleName();
    private Model model;


    @Override
    public final void onCreate(Bundle savedInstanceState) {
        if(model == null) {
            model = configurationModel();
        }
        model.onCreateBefore();
        super.onCreate(savedInstanceState);
        setContentView(model.getContentView());
        ButterKnife.bind(model,getWindow().getDecorView());
        model.onCreate(this,savedInstanceState);
    }

    @Override
    protected final void onResume() {
        super.onResume();
        model.onResume();
    }

    @Override
    protected final void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(model);
        model.onDestroy();
    }

    @Override
    protected final void onStop() {
        super.onStop();
        model.onStop();
    }

    @Override
    protected final void onRestart() {
        super.onRestart();
        model.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        model.onStart();
    }

    @Override
    protected final void onPause() {
        super.onPause();
        model.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        model.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);

    }

    public Model getModel() {
        return model;
    }

    protected abstract Model configurationModel();

}
