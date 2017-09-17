package com.lin.app.model.support;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lin.app.R;

/**
 * Created by Hui on 2017/9/16.
 */

public class LifeFragment implements IChildSelect,IActivityImpl {


    private IActivityImpl activity;
    private View contentView;

    public LifeFragment(IActivityImpl activity) {
        this.activity = activity;
        contentView = new View(activity.getActivity());
    }

    @Override
    public void loadData() {

    }

    public void init(){

    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_life;
    }

    @Override
    public void finish() {

    }

    @Override
    public View getFragment() {
        return contentView;
    }

    @Override
    public String getTitle() {
        return "生活热点";
    }

    @Override
    public AppCompatActivity getActivity() {
        return null;
    }

    @Override
    public void close() {

    }
}
