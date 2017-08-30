package com.lin.app.model;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lin.alllib.Model;
import com.lin.alllib.common.ScreenUtil;
import com.lin.app.R;
import com.lin.alllib.data.respone.CityRespone;
import com.lin.app.common.GlideCircleTransform;
import com.lin.app.data.entity.NotifyInfoEntity;
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
    RecyclerView recyclerView;
    private List<NotifyInfoEntity> datas = new ArrayList<>();


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.top = ScreenUtil.dp2px(20);
                outRect.left = ScreenUtil.dp2px(20);
                outRect.right = ScreenUtil.dp2px(20);
                if (parent.getChildAdapterPosition(view) + 1 == datas.size()) {
                    outRect.bottom = ScreenUtil.dp2px(20);
                }
            }
        });
        recyclerView.setAdapter(new BaseQuickAdapter<NotifyInfoEntity, BaseViewHolder>(R.layout.adapter_main, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final NotifyInfoEntity notifyInfoEntity) {
//                baseViewHolder.setText(R.id.item_main_text_id, notifyInfoEntity.getContentText());
//                baseViewHolder.setText(R.id.item_main_time_id, notifyInfoEntity.getTime());
//                baseViewHolder.setText(R.id.item_main_title_id, notifyInfoEntity.getTitle());
                Glide.
                        with(getActivity()).
                        load(notifyInfoEntity.getPic_rs()).
                        bitmapTransform(new GlideCircleTransform(getActivity())).
                        crossFade(1000).
                        into((ImageView) baseViewHolder.getView(R.id.item_main_iv_id));
                baseViewHolder.setOnClickListener(R.id.root_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSnackbar(notifyInfoEntity.toString());
                    }
                });
            }
        });

    }

    @Override
    protected void loadData() {
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.one_navigation));
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.three_navigation));
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.tow_navigation));
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.ic_launcher));
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.tow_navigation));
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.one_navigation));
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.three_navigation));
        datas.add(new NotifyInfoEntity().setPic_rs(R.mipmap.tow_navigation));
    }
}
