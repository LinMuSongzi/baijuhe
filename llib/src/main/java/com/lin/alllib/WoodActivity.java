package com.lin.alllib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.lin.alllib.data.EmptyEntity;
import com.lin.alllib.framwork.DebugGod;
import com.lin.alllib.framwork.commander.IDeal;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by lpds on 2017/7/26.
 */
public abstract class WoodActivity<T,M extends Model<?>> extends AppCompatActivity implements IDeal<T> {

    protected final String TAG = getClass().getSimpleName();
    private M model;
    private Toolbar t;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        if (model == null) {
            model = configurationModel();
        }

        model.onCreateBefore();
        super.onCreate(savedInstanceState);
        setContentView(model.getContentView());
        checkBar();
        ButterKnife.bind(model, getWindow().getDecorView());
        model.onCreate(this, savedInstanceState);
        model.loadData();
    }

    private void checkBar(){
        View v = findViewById(R.id.lib_toolbar);
        if(v == null){
            return;
        }else if(!(v instanceof Toolbar)){
            throw new ClassCastException("The id(R.id.lib_toolbar) must be is android.support.v7.widget.Toolbar.java");
        }else{
            t = (Toolbar) v;
            setSupportActionBar(t);
        }
    }

    public boolean isHadBar(){
        return t != null;
    }


    public Toolbar getToolbar() {
        return t;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (model != null) {
            return model.onCreateOptionsMenu(menu);
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (model != null) {
            return model.onOptionsItemSelected(item);
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    public M getModel() {
        return model;
    }

    protected abstract M configurationModel();


    @Override
    public String getValueOfString(String key) {
        return null;
    }

    @Override
    public int getValueOfInteger(String key) {
        return 0;
    }

    @Override
    public float getValueOfFloat(String key) {
        return 0;
    }

    @Override
    public Object getValueOfObject(String key) {
        return null;
    }

    @Override
    public T getAffirmObject() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(EmptyEntity emptyEntity) {
        DebugGod.i(TAG, "EmptyEntity");
    }
}
