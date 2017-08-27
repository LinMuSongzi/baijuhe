package com.lin.app.model;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lin.alllib.Model;
import com.lin.app.R;
import com.lin.alllib.data.respone.CityRespone;
import com.lin.app.request.ApiImp;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by lpds on 2017/7/26.
 */
public class MainModel extends Model {

    @Bind(R.id.id_content_RecyclerView)
    RecyclerView id_content_RecyclerView;

    private CityRespone cityRespone;
    private List<CityRespone.ResultBean> provinces = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        id_content_RecyclerView.setLayoutManager(linearLayoutManager);
        id_content_RecyclerView.setAdapter(new BaseQuickAdapter<CityRespone.ResultBean, BaseViewHolder>(R.layout.item_provinces, provinces) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, CityRespone.ResultBean province) {
                baseViewHolder.setText(R.id.id_provinces_tv, province.getProvince());
                String color = "#" + String.valueOf((int) ((Math.random() * 8 +1 ) * 100000));
                System.err.println(color);
                baseViewHolder.setBackgroundColor(R.id.id_provinces_tv, Color.parseColor(color));
//                baseViewHolder.getView()
            }
        });
        ((BaseQuickAdapter)id_content_RecyclerView.getAdapter()).openLoadAnimation();
        id_content_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        ApiImp.getAllCity();
        setSystemUiVisibility(false);
    }

    private void getProvince() {
        provinces.clear();
        provinces.addAll(cityRespone.getResult());
        id_content_RecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(CityRespone cityRespone) {

        this.cityRespone = cityRespone;
        getProvince();
    }


}