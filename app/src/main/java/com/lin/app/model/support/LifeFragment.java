package com.lin.app.model.support;

import android.support.v4.app.Fragment;

import com.lin.app.R;

/**
 * Created by Hui on 2017/9/16.
 */

public class LifeFragment extends Fragment implements IChildSelect {
    @Override
    public void loadData() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_life;
    }

    @Override
    public void finish() {

    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getTitle() {
        return "生活热点";
    }
}
