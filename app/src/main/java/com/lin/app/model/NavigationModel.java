package com.lin.app.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lin.alllib.Model;
import com.lin.app.R;
import com.lin.app.activity.fragment.NavigationFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by linhui on 2017/8/10.
 */
public class NavigationModel extends Model {

    private Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_navigatiom;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        toolbar = getToolbar();

    }
}
