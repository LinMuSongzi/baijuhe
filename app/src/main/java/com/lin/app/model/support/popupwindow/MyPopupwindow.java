package com.lin.app.model.support.popupwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.PopupMenuCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.lin.alllib.common.ScreenUtil;
import com.lin.app.R;
import com.lin.app.common.WindowUtil;
import com.lin.app.model.SelectInfoModel;
import com.lin.app.model.support.IActivityImpl;
import com.lin.app.model.support.ISelecImpl;
import com.lin.app.model.support.SupportSelectInfo;

/**
 * Created by Hui on 2017/9/17.
 */

public class MyPopupwindow implements IPopup, IActivityImpl {

    private static final String TAG = "MyPopupwindow";
    private AppCompatActivity activity;
    private ISelecImpl supportSelectInfo;
    private PopupWindow popupWindow;
    private View contentView;

    private PopupListener popupListener = new PopupListener() {
        @Override
        public void show() {
            Log.i(TAG, "show: ");
        }

        @Override
        public void dismiss() {
            Log.i(TAG, "dismiss: ");
        }
    };

    public MyPopupwindow(AppCompatActivity activity) {
        this.activity = activity;
        supportSelectInfo = new SupportSelectInfo(this);
    }

    @Override
    public void loadData() {
        checkInit();
        supportSelectInfo.init(contentView);
        supportSelectInfo.loadData();
    }

    @Override
    public void onDestroy() {
        supportSelectInfo.onDestroy();
    }

    @Override
    public int getContentView() {
        return supportSelectInfo.getContentView();
    }

    @Override
    public void finish() {

    }

    @Override
    public void show() {
        checkInit();
        popupWindow.showAtLocation(activity.getWindow().getDecorView(),Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void dismiss() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    private synchronized void checkInit() {

        if (popupWindow == null && contentView == null) {
            contentView = LayoutInflater.from(activity).inflate(getContentView(), null, false);
            popupWindow = new PopupWindow(contentView, ScreenUtil.getScreenWidth()
                    , (int) (ScreenUtil.getScreenHeight() * 0.75),true) {

                @Override
                public void dismiss() {
                    if (popupListener != null) {
                        popupListener.dismiss();
                    }
                    WindowUtil.removeShade(activity);
                    super.dismiss();
                }

                @Override
                public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
                    if (popupListener != null) {
                        popupListener.show();
                    }
                    WindowUtil.addShade(activity);
                    super.showAsDropDown(anchor, xoff, yoff, gravity);
                }

                @Override
                public void showAtLocation(View parent, int gravity, int x, int y) {
                    if (popupListener != null) {
                        popupListener.show();
                    }
                    WindowUtil.addShade(activity);
                    super.showAtLocation(parent, gravity, x, y);
                }
            };
            popupWindow.setOutsideTouchable(false);
            popupWindow.setAnimationStyle(R.style.take_photo_anim);
        }

    }

    @Override
    public AppCompatActivity getActivity() {
        return activity;
    }

    @Override
    public void close() {
        dismiss();
    }

    @Override
    public void setPopupListener(PopupListener popupListener) {
        this.popupListener = popupListener;
    }
}
