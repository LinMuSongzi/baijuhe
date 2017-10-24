package com.mr.huang.activity;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mr.huang.BaseActivity;
import com.mr.huang.R;
import com.mr.huang.common.ShowTip;
import com.mr.huang.data.entity.IntegerEntity;
import com.mr.huang.data.entity.NumberAttributeEntity;
import com.mr.huang.data.entity.SaveEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;

/**
 * Created by linhui on 2017/9/29.
 */
public class NumberInfoActivity extends BaseActivity {

    @Bind(R.id.id_content_RecyclerView)
    RecyclerView id_content_RecyclerView;
    @Bind(R.id.id_numberlist_title)
    EditText id_numberlist_title;
    @Bind(R.id.id_numberlist_all_money)
    EditText id_numberlist_all_money;
    @Bind(R.id.root_layout)
    View root_layout;

    private NumberAttributeEntity attributeEntity;
    private String title = "";
    private List<IntegerEntity> integerList = new ArrayList<IntegerEntity>() {
        @Override
        public boolean add(IntegerEntity o) {
            boolean flag = true;
            while (flag) {
                flag = false;
                int count = o.getElement();
                for (IntegerEntity i : integerList) {
                    if (i.getElement() == count) {
                        o.setElement(count + 1);
                        flag = true;
                        break;
                    }
                }
            }
            return super.add(o);
        }
    };


    @Override
    public void init() {
        attributeEntity = (NumberAttributeEntity) getIntent().getSerializableExtra("NumberAttributeEntity");
        if (attributeEntity != null) {
            title = "集合详情";
            integerList.addAll(attributeEntity.getIntegers());
            Collections.sort(integerList, new Comparator<IntegerEntity>() {
                @Override
                public int compare(IntegerEntity o1, IntegerEntity o2) {
                    return o1.getElement() - o2.getElement() ;
                }
            });
            id_numberlist_title.setText(attributeEntity.getName());
            id_numberlist_all_money.setText(attributeEntity.getPrices()+"");
        } else {
            title = "新增集合";
        }



        Toolbar t = (Toolbar) findViewById(R.id.lib_toolbar);
        t.setTitle(title);
        t.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(t);
        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(NumberInfoActivity.this).setNegativeButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setMessage("是否退出编辑？").create();
                alertDialog.show();
            }
        });


        id_content_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        id_content_RecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        id_content_RecyclerView.setAdapter(new RecyclerView.Adapter<NumberInfoHolder>() {
            @Override
            public NumberInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {


                NumberInfoHolder numberInfoHolder =
                        new NumberInfoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_number_info, parent, false));


                return numberInfoHolder;
            }

            @Override
            public void onBindViewHolder(final NumberInfoHolder holder, final int position) {
                holder.id_number.setText(integerList.get(position).getElement() + "");
                holder.id_number.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog alertDialog = new AlertDialog.Builder(NumberInfoActivity.this).setNegativeButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                integerList.remove(integerList.get(position));
                                notifyDataSetChanged();
                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).setMessage("是否删除此数字？").create();

                        alertDialog.show();



                        return true;
                    }
                });
                holder.id_number.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(holder.id_number.hasFocus()
                                && holder.id_number.hasFocusable()) {
                            if(s.toString().isEmpty()){
                                return;
                            }
                            integerList.get(position).setElement(Integer.parseInt(s.toString()));
                        }
                    }
                });
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

        switch (item.getItemId()) {
            case R.id.add:
                integerList.add(new IntegerEntity(integerList.size()+1));
                id_content_RecyclerView.getAdapter().notifyDataSetChanged();
                break;
            case R.id.save:
                SaveEntity saveEntity = checkSave();
                if (saveEntity != null) {
                    EventBus.getDefault().post(saveEntity);
                    finish();
                }
                break;
        }

        return true;
    }

    private SaveEntity checkSave() {
        if (id_numberlist_all_money.getText().toString().isEmpty()) {
            ShowTip.showSnackbar(root_layout, "金额不能为空！");
        } else if (id_numberlist_title.getText().toString().isEmpty()) {
            Snackbar.make(root_layout, "标题不能为空！", 1500).show();
        } else if (integerList.size() == 0) {
            ShowTip.showSnackbar(root_layout, "集合不能为空！");
        } else {
            SaveEntity s = new SaveEntity();
            s.setName(id_numberlist_title.getText().toString());
            Set<IntegerEntity> set = new HashSet<IntegerEntity>();
            set.addAll(integerList);
            s.setIntegers(set);
            s.setPrices(new Integer(id_numberlist_all_money.getText().toString()));

            if(attributeEntity == null){
                s.setAdd(true);
            }else{
                s.setAdd(false);
            }


            return s;
        }

        return null;

    }

    private static final class NumberInfoHolder extends RecyclerView.ViewHolder {

        private EditText id_number;


        public NumberInfoHolder(View itemView) {
            super(itemView);
            id_number = (EditText) itemView.findViewById(R.id.id_number);
        }
    }

}
