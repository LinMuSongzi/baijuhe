package com.lin.app.model.support;

import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lin.alllib.data.EmptyEntity;
import com.lin.app.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @OnClick(R.id.close_iv)
    public void closePopup(){
        close();
    }


    public SupportSelectInfo(IActivityImpl activity) {
        this.iActivity = activity;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_select_info;
    }

    @Override
    public void init(View view) {
        FRAGMENTS.add(new GameFragment(this));
        FRAGMENTS.add(new LifeFragment(this));
        ((GameFragment)FRAGMENTS.get(0)).onCreateView(LayoutInflater.from(getActivity()),null,null);
        if(view == null) {
            ButterKnife.bind(this, iActivity.getActivity().getWindow().getDecorView());
        }else{
            ButterKnife.bind(this, view);
        }
        EventBus.getDefault().register(this);
        id_viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }
            // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup view, int position, Object object) {

                view.removeView(FRAGMENTS.get(position).getFragment());
            }

            // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
            @Override
            public Object instantiateItem(ViewGroup view, int position) {
                view.addView(FRAGMENTS.get(position).getFragment());
                return FRAGMENTS.get(position).getFragment();
            }

        });
        id_tabLayout.setupWithViewPager(id_viewPager);id_tabLayout.setSelectedTabIndicatorColor(ActivityCompat.getColor(getActivity(),R.color.bg_main_blue));
        for(int i = 0;i < id_tabLayout.getTabCount();i++){
            id_tabLayout.getTabAt(i).setText(FRAGMENTS.get(i).getTitle());
        }
    }

    @Override
    @Deprecated
    public void init() {
        init(null);
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
        iActivity.getActivity().finish();
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
    public AppCompatActivity getActivity() {
        return iActivity.getActivity();
    }

    @Override
    public void close() {
        iActivity.close();
    }
}
