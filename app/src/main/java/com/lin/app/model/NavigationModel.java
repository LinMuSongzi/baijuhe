package com.lin.app.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

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
    @Bind(R.id.id_navigation_ViewPager)
    ViewPager id_navigation_ViewPager;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_navigatiom;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        NavigationFragment navigationFragment1 = new NavigationFragment();
        navigationFragment1.setData(R.mipmap.one_navigation);
        fragmentList.add(navigationFragment1);

        NavigationFragment navigationFragment2 = new NavigationFragment();
        navigationFragment2.setData(R.mipmap.tow_navigation);
        fragmentList.add(navigationFragment2);

        NavigationFragment navigationFragment3 = new NavigationFragment();
        navigationFragment3.setData(R.mipmap.three_navigation);
        fragmentList.add(navigationFragment3);

        id_navigation_ViewPager.setAdapter(new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

    }
}
