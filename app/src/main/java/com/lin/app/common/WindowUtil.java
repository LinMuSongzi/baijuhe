package com.lin.app.common;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by Hui on 2017/9/17.
 */

public final class WindowUtil {

    private static final int SHADE_VIEW_ID = 0x98a123;

    private WindowUtil() {
    }


    public static void addShade(Activity activity) {
        View view = activity.getWindow().getDecorView().findViewById(SHADE_VIEW_ID);
        if (activity == null
                || view != null) {
            return;
        }

        view = new View(activity);
        view.setId(SHADE_VIEW_ID);
        view.setBackgroundColor(Color.parseColor("#99333333"));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.MATCH_PARENT);
        ((ViewGroup) activity.getWindow().getDecorView()).addView(view, layoutParams);


    }


    public static void removeShade(Activity activity) {
        View view = activity.getWindow().getDecorView().findViewById(SHADE_VIEW_ID);
        if (activity == null
                || view == null) {
            return;
        }
        ((ViewGroup) activity.getWindow().getDecorView()).removeView(view);
    }

}
