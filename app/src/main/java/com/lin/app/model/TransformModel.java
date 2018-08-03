package com.lin.app.model;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeClipBounds;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lin.alllib.Model;
import com.lin.app.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by linhui on 2018/7/2.
 */
public class TransformModel extends Model<VideoDemoModel> {

    @Bind(R.id.id_content_layout)
    ViewGroup id_content_layout;
    @Bind(R.id.id_content_iv)
    ImageView id_content_iv;

    private int pos;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.id_content_iv)
    public void click(View v) {
//        ChangeBounds changeBounds=new ChangeBounds();
//        changeBounds.setDuration(2000);
//        TransitionManager.beginDelayedTransition(id_content_layout,changeBounds);
//        id_content_layout.setPadding(100,100,100,100);



    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void change(boolean is, int action) {


        Rect rect = buildRect(false);
//        Log.i(TAG, "onTouch: action = " + action + " , " + rect + " ,sum = " + sum);
        if (is) {
            ChangeClipBounds changeClipBounds = new ChangeClipBounds();
            android.transition.TransitionManager.beginDelayedTransition(id_content_layout, changeClipBounds);
        }
//        if (pos % 2 == 0) {
        ViewCompat.setClipBounds(id_content_iv, rect);
//        } else {
//            ViewCompat.setClipBounds(id_content_iv, null);
//        }
//        pos++;

    }

    private Rect buildRect(boolean isCrush) {
        int w = id_content_iv.getWidth();
        int h = id_content_iv.getHeight();
        int sum = (w - b.outWidth) / 2;
        if (isCrush) {
            return new Rect(x - sum - 150, y - 150, x + 150 - sum, y + 150);
        } else {
            int left;
            int top;
            int right;
            int bottom;
            if (x + 150 > w) {
                right = w;
                left = w - 300;
            } else if (x - sum < sum) {
                left = sum;
                right = sum + 300;
            } else {
                left = x - 150 - sum;
                right = x + 150 - sum;
            }

            if (y + 150 > h) {
                top = h - 300;
                bottom = h;
            } else if (y - 150 < 0) {
                top = 0;
                bottom = 300;
            } else {
                top = y - 150;
                bottom = y + 150;
            }
            return new Rect(left, top, right, bottom);
        }


    }


    @Override
    protected int getContentView() {
        return R.layout.activity_transform;
    }

    private int x;
    private int y;

    private BitmapFactory.Options b;

    @Override
    protected void init(Bundle savedInstanceState) {
        b = new BitmapFactory.Options();
        b.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.timg, b);


        id_content_iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                x = (int) event.getX();
                y = (int) event.getY();
                Log.i(TAG, "onTouch: x = " + x + " ,  y = " + y);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        change(true, MotionEvent.ACTION_DOWN);
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        if (pos % 2 == 0) {
//
//                        }
//                        Log.i(TAG, "onTouch: x = " + x + " ,  y = " + y);
                        change(false, MotionEvent.ACTION_DOWN);
                        break;
                    case MotionEvent.ACTION_UP:
                        change(true, MotionEvent.ACTION_UP);
                        ViewCompat.setClipBounds(id_content_iv, null);
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public VideoDemoModel getAffirmObject() {
        return null;
    }
}
