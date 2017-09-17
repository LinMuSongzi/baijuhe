package com.lin.app.model.support;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lin.alllib.data.EmptyEntity;
import com.lin.app.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Hui on 2017/9/16.
 */

public final class SupportSelectInfo implements ISelecImpl{

    private IActivityImpl iActivity;
    private final List<IChildSelect> FRAGMENTS = new ArrayList<>();

    @Bind(R.id.id_tabLayout)
    TabLayout id_tabLayout;
    @Bind(R.id.id_viewPager)
    ViewPager id_viewPager;


    public SupportSelectInfo(IActivityImpl activity) {
        this.iActivity = activity;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_select_info;
    }

    @Override
    public void init() {
        FRAGMENTS.add(new GameFragment());
        FRAGMENTS.add(new LifeFragment());
        ButterKnife.bind(this,iActivity.getAtivity().getWindow().getDecorView());
        EventBus.getDefault().register(this);
        id_viewPager.setAdapter(new FragmentPagerAdapter(getAtivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FRAGMENTS.get(position).getFragment();
            }

            @Override
            public int getCount() {
                return FRAGMENTS.size();
            }
        });
        id_tabLayout.setupWithViewPager(id_viewPager);id_tabLayout.setSelectedTabIndicatorColor(ActivityCompat.getColor(getAtivity(),R.color.bg_main_blue));
        for(int i = 0;i < id_tabLayout.getTabCount();i++){
            id_tabLayout.getTabAt(i).setText(FRAGMENTS.get(i).getTitle());
        }
    }

    @Override
    public void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessgae(EmptyEntity emptyEntity){

    }


    @Override
    public void finish() {
        iActivity.getAtivity().finish();
    }

    @Override
    public void refresh(IInfoImpl iInfo) {

    }

    @Override
    public void loadData() {
        for(IChildSelect iChildSelect : FRAGMENTS){
            iChildSelect.loadData();
        }

    }


    @Override
    public AppCompatActivity getAtivity() {
        return iActivity.getAtivity();
    }
}
