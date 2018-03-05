package com.lin.app.model;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lin.alllib.Model;
import com.lin.app.BuildConfig;
import com.lin.app.R;
import com.lin.app.activity.TowActivity;
import com.lin.app.common.FileCopyManager;
import com.lin.app.data.respone.InitEntity;
import com.lin.app.ui.fragment.TabFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Hui on 2017/8/22.
 */

public class DianBaiModel extends Model {

    private static String TAG = "DianBaiModel";
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

            id_viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {


                    Intent intent = new Intent(getActivity(), TowActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("int", 0);
                    b.putFloat("float", 0.1f);
                    b.putDouble("double", 0.1d);
                    b.putString("string", "988");
                    intent.putExtra("asd",b);
                    intent.putExtra("String","字符串");
                    intent.putExtra("int",123);
                    intent.putExtra("double",1113.12d);
                    intent.putExtra("float",6522.365);

                    Log.i(TAG, "onPageSelected: " + intent.toURI());


                    new Dialog(getActivity(),0).show();

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        }

        /**
         * 是否禁用某项操作
         */
        @TargetApi(Build.VERSION_CODES.KITKAT)
        public boolean isAllowed(Context context, int op) {
            Log.d(TAG, "api level: " + Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT < 19) {
                return true;
            }
            Log.d(TAG, "op is " + op);
            String packageName = context.getApplicationContext().getPackageName();
            AppOpsManager aom = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            Class<?>[] types = new Class[]{int.class, int.class, String.class};
            Object[] args = new Object[]{op, Binder.getCallingUid(), packageName};
            try {
                Method method = aom.getClass().getDeclaredMethod("checkOpNoThrow", types);
                Object mode = method.invoke(aom, args);
                Log.d(TAG, "invoke checkOpNoThrow: " + mode);
                if ((mode instanceof Integer) && ((Integer) mode == AppOpsManager.MODE_ALLOWED)) {
                    Log.i(TAG, "allowed");
                    return true;
                } else {
                    Log.i(TAG, "no allowed");
                }
            } catch (Exception e) {
                Log.e(TAG, "invoke error: " + e);
                e.printStackTrace();
            }
            return false;
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
