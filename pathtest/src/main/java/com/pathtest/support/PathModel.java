package com.pathtest.support;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.animation.AnimatorUpdateListenerCompat;
import android.support.v4.animation.ValueAnimatorCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.lin.alllib.Model;
import com.pathtest.R;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.Bind;

/**
 * Created by linhui on 2017/9/7.
 */
public class PathModel extends Model<IPathModel> implements IPathModel, SurfaceHolder.Callback, ValueAnimator.AnimatorUpdateListener {

    @Bind(R.id.id_surfaceView)
    SurfaceView id_surfaceView;
    private final Paint mPaint = new Paint();
    private SurfaceHolder surfaceHolder;
    private AtomicBoolean isStart = new AtomicBoolean(false);
    private ValueAnimator valueAnimator;
    private int MID_WIDTH;
    private int MID_HEIGHT;
//    private final Path path = new Path();

    /**
     * 顺时针
     */
    private PathMeasure pathMeasure;

    /**
     * 逆时针
     */
    private PathMeasure pathMeasure2;


    Float[] abc1 = new Float[3];


    @Override
    protected int getContentView() {
        return R.layout.activity_scrolling;
    }

    private void initPath() {
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(15);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        Path path = new Path();

//        path.addCircle(MID_WIDTH / 2, MID_HEIGHT / 2, 200, Path.Direction.CW);
        path.addCircle(MID_WIDTH / 2, MID_HEIGHT / 2, 200, Path.Direction.CW);

        pathMeasure = new PathMeasure(path, false);

        path = new Path();

        path.addCircle(MID_WIDTH / 2, MID_HEIGHT / 2, 200, Path.Direction.CCW);
        pathMeasure2 = new PathMeasure(path, false);


        abc1[0] = 300f;
        abc1[1] = 250f;
        abc1[2] = pathMeasure.getLength();


        maxS = pathMeasure.getLength() / 9;

    }

    float maxS;

    @Override
    protected void init(Bundle savedInstanceState) {
        initPath();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getActivity().setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "请确认开始动画！", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if (MID_WIDTH == 0) {
                                    MID_WIDTH = id_surfaceView.getMeasuredWidth() / 2;
                                    MID_HEIGHT = id_surfaceView.getMeasuredHeight() / 2;
                                }

                                startDraw();
                            }
                        }).show();
            }
        });

        id_surfaceView.getHolder().addCallback(this);

    }

    @Override
    public void startDraw() {
        isStart.set(true);
        synchronized (id_surfaceView) {
            if (valueAnimator == null) {
                valueAnimator = ValueAnimator.ofFloat(
                        0.0f,
                        pathMeasure.getLength() / 4,
                        pathMeasure.getLength() / 2,
                        pathMeasure.getLength() / 2 / 4 + pathMeasure.getLength() / 2,
                        pathMeasure.getLength() / 2 / 2 + pathMeasure.getLength() / 2,
                        pathMeasure.getLength() + pathMeasure.getLength() / 2 / 4,
                        pathMeasure.getLength() + pathMeasure.getLength() / 2 / 2,
                        pathMeasure.getLength() + pathMeasure.getLength() / 3,
                        pathMeasure.getLength() * 2);

                valueAnimator.setDuration(3_000);
                valueAnimator.setRepeatCount(-1);
//                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
                valueAnimator.addUpdateListener(this);
            }
            if (!valueAnimator.isRunning()) {
                valueAnimator.start();
            }
        }
    }

    @Override
    public void endDraw() {
        isStart.set(false);
        valueAnimator.cancel();
//        surfaceHolder.lockCanvas()
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        synchronized (id_surfaceView) {
            this.surfaceHolder = surfaceHolder;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        synchronized (id_surfaceView) {
            endDraw();
            this.surfaceHolder = null;
        }
    }


    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float values = (Float) valueAnimator.getAnimatedValue();
        float time = valueAnimator.getDuration();
        if (surfaceHolder != null) {

            final Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            Path path = new Path();

            float s = (float) (maxS * Math.sqrt(time));

            if (values < pathMeasure.getLength()) {
//                pathMeasure.getSegment(0, s, path, true);
                pathMeasure.getSegment(0, values, path, true);
            } else {
                pathMeasure2.getSegment(0, pathMeasure2.getLength() * 2 - values, path, true);
            }

//            float a = values * 1f / pathMeasure.getLength();
//            path.offset(MID_WIDTH, MID_HEIGHT * a/2);

            path.offset(MID_WIDTH, MID_HEIGHT);

            canvas.drawPath(path, mPaint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

}
