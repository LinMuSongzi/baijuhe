package com.lin.app.activity.fragment;

import com.lin.alllib.framwork.FragmentModel;
import com.lin.alllib.WoodFragment;
import com.lin.alllib.framwork.commander.IShipment;
import com.lin.app.model.fragment.NavigationFragmentModel;

/**
 * Created by linhui on 2017/8/10.
 */
public class NavigationFragment extends WoodFragment implements IShipment<Integer>{

    NavigationFragmentModel navigationFragmentModel = new NavigationFragmentModel();

    @Override
    protected FragmentModel getFragmentModel() {
        return navigationFragmentModel;
    }

    @Override
    public void setData(Integer integer) {
        navigationFragmentModel.setData(integer);
    }
}
