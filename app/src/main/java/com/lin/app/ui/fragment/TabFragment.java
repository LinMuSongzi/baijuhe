package com.lin.app.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lin.alllib.WoodFragment;
import com.lin.alllib.framwork.FragmentModel;
import com.lin.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by linhui on 2017/12/6.
 */
public class TabFragment extends Fragment {


    View contentView;
//    @Bind(R.id.id_text)
//    TextView id_text;

    @Bind(R.id.id_content_RecyclerView)
    RecyclerView id_content_RecyclerView;

    List<String> stringList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_tab, container, false);
            ButterKnife.bind(this, contentView);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    init();
                }
            });
        }
//        id_text.setText("" + getArguments().getInt("number"));

        return contentView;
    }

    private void init() {
        convert();
        id_content_RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        id_content_RecyclerView.setItemAnimator(new DefaultItemAnimator());
        id_content_RecyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.adapter_text, stringList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String s) {
                baseViewHolder.setText(R.id.id_txt,s);
            }
        });
    }

    private void convert() {


        int i = 30;

        while(i > 0){

            stringList.add(i+"");

            i--;
        }


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
