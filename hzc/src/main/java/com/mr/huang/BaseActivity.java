package com.mr.huang;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mr.huang.data.entity.NUll;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;

/**
 * Created by linhui on 2017/9/29.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeOnCreate();
        setContentView(getContentView());
        ButterKnife.bind(this);
        init();
    }



    @Override
    public final void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    protected void beforeOnCreate() {

    }
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(NUll nUll){}

    protected abstract void init();

    protected abstract int getContentView();
}
