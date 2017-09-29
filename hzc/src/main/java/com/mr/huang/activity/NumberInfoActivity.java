package com.mr.huang.activity;

import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mr.huang.BaseActivity;
import com.mr.huang.R;
import com.mr.huang.data.entity.NumberAttributeEntity;
import com.mr.huang.data.entity.SaveEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;

/**
 * Created by linhui on 2017/9/29.
 */
public class NumberInfoActivity extends BaseActivity {

    @Bind(R.id.id_content_RecyclerView)
    RecyclerView id_content_RecyclerView;

    private NumberAttributeEntity attributeEntity;

    private List<Integer> integerList = new ArrayList<>();

    @Override
    public void init() {


        Toolbar t = (Toolbar) findViewById(R.id.lib_toolbar);
        t.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(t);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).setNegativeButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new SaveEntity());
                        dialog.dismiss();
                        finish();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("是否保存？").create();

                alertDialog.show();
            }
        });

        attributeEntity = (NumberAttributeEntity) getIntent().getSerializableExtra("NumberAttributeEntity");
        if(attributeEntity!=null){
            integerList.addAll(attributeEntity.getIntegers());
        }

        id_content_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        id_content_RecyclerView.setLayoutManager(new GridLayoutManager(this,5));
        id_content_RecyclerView.setAdapter(new RecyclerView.Adapter<NumberInfoHolder>() {
            @Override
            public NumberInfoHolder  onCreateViewHolder(ViewGroup parent, int viewType) {


                NumberInfoHolder numberInfoHolder = new NumberInfoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_number_info,parent));


                return numberInfoHolder;
            }

            @Override
            public void onBindViewHolder(NumberInfoHolder holder, int position) {
                holder.id_number.setText(integerList.get(position)+"");
            }

            @Override
            public int getItemCount() {
                return integerList.size();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_numberinfo;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.lib_toolbar){

            integerList.add(0);
            id_content_RecyclerView.getAdapter().notifyDataSetChanged();

        }

        return true;
    }

    private static final class NumberInfoHolder extends RecyclerView.ViewHolder{

        private EditText id_number;


        public NumberInfoHolder(View itemView) {
            super(itemView);
            id_number = (EditText) itemView.findViewById(R.id.id_number);
        }
    }

}
