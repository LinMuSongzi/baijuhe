package com.lin.app.model;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.lin.alllib.Model;
import com.lin.app.R;
import com.lin.app.data.respone.InitEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * Created by Hui on 2017/8/22.
 */

public class DianBaiModel extends Model {

    @Bind(R.id.id_share_floatingActionButton)
    FloatingActionButton id_share_floatingActionButton;

    @Override
    protected int getContentView(){
        return R.layout.activity_dianbai;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getActivity().setTitle(R.string.my_ity);
        id_share_floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v)
                Toast.makeText(v.getContext(),"hello",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public Object getAffirmObject() {
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(InitEntity strEntity) {

        if (strEntity.getRequet() != null) {
            showMsg(strEntity.toString());
        }

    }

}
