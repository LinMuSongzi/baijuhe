package com.lin.app.model.fragment;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lin.alllib.framwork.FragmentModel;
import com.lin.alllib.framwork.commander.IShipment;
import com.lin.app.R;

import butterknife.Bind;

/**
 * Created by linhui on 2017/8/10.
 */
public class NavigationFragmentModel extends FragmentModel implements IShipment<Integer>{

    @Bind(R.id.id_content_iv)
    ImageView id_content_iv;

    private int resource;

    @Override
    protected int getContentView() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Glide.with(getFrament()).load(resource).fitCenter().into(id_content_iv);
    }

    @Override
    public void setData(Integer integer) {
        resource = integer;
    }
}
