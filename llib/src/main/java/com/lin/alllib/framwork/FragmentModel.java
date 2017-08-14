package com.lin.alllib.framwork;

import android.support.v4.app.Fragment;

import com.lin.alllib.Model;
import com.lin.alllib.data.EmptyEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by linhui on 2017/8/10.
 */
public abstract class FragmentModel extends Model {

    private Fragment fragment;

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public void onDestroyView(){}

    public Fragment getFrament(){
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(EmptyEntity emptyEntity){

    }


}
