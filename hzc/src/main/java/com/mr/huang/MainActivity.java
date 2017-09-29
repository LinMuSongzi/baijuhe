package com.mr.huang;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mr.huang.activity.NumberInfoActivity;
import com.mr.huang.common.SaveUitl;
import com.mr.huang.common.ScreenUtil;
import com.mr.huang.data.entity.NumberAllEntity;
import com.mr.huang.data.entity.NumberAttributeEntity;
import com.mr.huang.data.entity.SaveEntity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.id_content_RecyclerView)
    RecyclerView id_content_RecyclerView;

    private final List<NumberAttributeEntity> datas = new ArrayList<>();

    private NumberAttributeEntity editEntity = null;


    @Override
    public void init() {


        Toolbar t = (Toolbar) findViewById(R.id.lib_toolbar);
        t.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(t);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
            }
        });
        t.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.add:
                        startActivity(new Intent(MainActivity.this, NumberInfoActivity.class));
                        break;
                    case R.id.save:
                        save();
                        break;
                }
                return true;
            }
        });

        getNumberData();

        id_content_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        id_content_RecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        id_content_RecyclerView.setAdapter(new BaseQuickAdapter<NumberAttributeEntity, BaseViewHolder>(R.layout.adapter_main, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, final NumberAttributeEntity appEntity) {

                baseViewHolder.setText(R.id.item_main_title_id,appEntity.getName());

                baseViewHolder.setOnClickListener(R.id.root_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editEntity = appEntity;
                        Intent i = new Intent(v.getContext(), NumberInfoActivity.class);
                        try {
                            i.putExtra("NumberAttributeEntity", (Serializable) appEntity.clone());
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                        startActivity(new Intent());
                    }
                });

            }
        });

    }

    private void save() {
        NumberAllEntity n = new NumberAllEntity();
        n.setLineEntityList(datas);
        SaveUitl.saveNumberList(n);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getNumberData();
                Snackbar.make(getWindow().getDecorView(),"保存成功",1500).show();
            }
        });

    }

    @Override
    protected void beforeOnCreate() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessage(SaveEntity saveEntity){
        editEntity.setIntegers(saveEntity.getIntegers());
        editEntity.setName(saveEntity.getName());
        editEntity.setPrices(saveEntity.getPrices());
        save();
    }


    public void getNumberData() {
        NumberAllEntity a = SaveUitl.getNumberList();
        if (a != null) {
            if (a.getLineEntityList() != null) {
                datas.clear();
                datas.addAll(a.getLineEntityList());
                if (id_content_RecyclerView.getAdapter() != null) {
                    id_content_RecyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        }
    }
}
