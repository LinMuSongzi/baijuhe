package com.dex.linhui.ui.model;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.dex.linhui.R;
import com.dex.linhui.framwork.dex.DexLoadUtil;
import com.dex.linhui.framwork.dex.ResouceDex;
import com.dex.linhui.ui.activity.NavigationActivity;
import com.dex.linhui.ui.activity.NavigationActivity.INavigationModel;
import com.lin.alllib.Model;
import com.lin.alllib.test.MainTest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.Bind;

/**
 * Created by linhui on 2017/10/17.
 */
public class NavigationModelImp extends Model<INavigationModel> implements INavigationModel {

    private Resources apkResources;

    private Object layout, anim, drawable;

    @Bind(R.id.center_iv)
    ImageView center_iv;

    @Override
    protected int getContentView() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT > 22) {
            getActivity().requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public INavigationModel getAffirmObject() {
        return this;
    }

    @Override
    public void onDex() {

        getLayout();

//        getContextActivity();

    }

    private void getLayout() {
        if (layout == null) {
            layout = DexLoadUtil.loadDex(DexLoadUtil.DEX_KEY_APP, "com.lin.app.R$layout");
        }
        if (apkResources == null) {
            apkResources = ResouceDex.newInstance(new File(DexLoadUtil.PARENT_FILE, DexLoadUtil.DEFUALT_APP_APK_NAME));
        }

        changeResource();

        Field field = null;
        try {
            field = layout.getClass().getDeclaredField("activity_card_1");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        View view = null;
        try {
            view = LayoutInflater.from(getActivity()).inflate(field.getInt(layout), null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        getActivity().setContentView(view);

    }

    private void getDrawable() {
        if (drawable == null) {
            drawable = DexLoadUtil.loadDex(DexLoadUtil.DEX_KEY_APP, "com.lin.app.R$drawable");
        }
        if (apkResources == null) {
            apkResources = ResouceDex.newInstance(new File(DexLoadUtil.PARENT_FILE, DexLoadUtil.DEFUALT_APP_APK_NAME));
        }


        try {
            Field field = drawable.getClass().getDeclaredField("ic_share_black_36dp");
            int values = field.getInt(drawable);
            Bitmap bitmap = BitmapFactory.decodeResource(apkResources, values);
            center_iv.setImageBitmap(bitmap);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void changeResource() {
        setSoucreField(Activity.class, "mResources");
    }

    public void setSoucreField(Class c, String fieldName) {
        try {
            Field field = c.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(getActivity(), apkResources);
        } catch (Exception ex) {
            ex.printStackTrace();
            Class s = c.getSuperclass();
            if (s != null) {
                setSoucreField(s, fieldName);
            }
        }
    }


//    public void getContextActivity() {
//       Activity mainActivity = (Activity) DexLoadUtil.loadDex(DexLoadUtil.DEX_KEY_APP,"com.lin.app.ui.activity.MainActivity");
//
//        mainActivity.getClass().getMethod("attach",)
//    }


}
