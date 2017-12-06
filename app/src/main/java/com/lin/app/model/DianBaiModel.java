package com.lin.app.model;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lin.alllib.Model;
import com.lin.app.R;
import com.lin.app.data.respone.InitEntity;
import com.lin.app.ui.fragment.TabFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Hui on 2017/8/22.
 */

public class DianBaiModel extends Model {

    @Bind(R.id.id_share_floatingActionButton)
    FloatingActionButton id_share_floatingActionButton;
    @Bind(R.id.id_tab_layout)
    TabLayout id_tab_layout;
    @Bind(R.id.id_viewpage)
    ViewPager id_viewpage;
    final List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_dianbai;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getActivity().setTitle(R.string.my_ity);
        id_share_floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        new ConvertSupport().init();
    }

    @Override
    public Object getAffirmObject() {
        return null;
    }


    final class ConvertSupport {

        void init() {
            fragmentList.clear();

            id_tab_layout.setupWithViewPager(id_viewpage);

            convertFragment();

            id_viewpage.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragmentList.get(position);
                }

                @Override
                public int getCount() {
                    return fragmentList.size();
                }
            });

            for (int i = 0; i < id_tab_layout.getTabCount(); i++) {
                id_tab_layout.getTabAt(i).setText(i + 1 + "");
            }

        }

        private void convertFragment() {
            int i = 1;
            while (i <= 5) {
                TabFragment tab = new TabFragment();
                Bundle b = new Bundle();
                b.putInt("number", i);
                tab.setArguments(b);
                fragmentList.add(tab);
                i++;
            }

        }


    }

}
