package com.activitys;

import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.common.SurfaceExecute;
import com.fileengine.R;
import com.lin.baiduapi.BaiduMapImp;
import com.lin.baiduapi.common.CommonUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by linhui on 2018/3/28.
 */
public class RxActivity extends AppCompatActivity {


    private static final String TAG = "RxActivity";

    private SurfaceView id_SurfaceView;
    private SurfaceExecute surfaceExecute;
    private Surface surface;
    private SurfaceHolder holder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        final Timer timer = new Timer();
        id_SurfaceView = (SurfaceView) findViewById(R.id.id_SurfaceView);
        id_SurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                RxActivity.this.holder = holder;
                surfaceExecute = new SurfaceExecute(
                        new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "test.mp4").getAbsolutePath(),
                        surface = holder.getSurface());
                surfaceExecute.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                surfaceExecute.stopCodec();
            }
        });
//        test5();
//        test4();
        final Paint paint = new Paint(Paint.UNDERLINE_TEXT_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sum--;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Canvas canvas = holder.lockCanvas(new Rect(0, 0, 720, 1020));
                        canvas.drawCircle(720 / 2, 1020 / 2, sum * 10, paint);
                        holder.unlockCanvasAndPost(canvas);
                    }
                });

                if (sum == 0) {
                    surfaceExecute.stopCodec();
                    timer.cancel();
                }
            }
        }, 500);

    }

    private int sum = 20;

    private void test5() {


//        new File(Environment.getExternalStorageDirectory()
//                .getAbsolutePath()+File.separator+"paimaster"+File.separator
//                +"video").listFiles(new FileFilter() {
//
//            @Override
//            public boolean accept(File pathname) {
//                test3(pathname.getAbsolutePath());
//                return false;
//            }
//        });

        new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator + "LuPingDaShi" + File.separator
                + "Rec").listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                test3(pathname.getAbsolutePath());
                return false;
            }
        });

    }

    private void test4() {
//        Cursor c = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null, null);
//
//        if (c.getCount() > 0 && c.moveToFirst()) {
//            do {
//                c.moveToNext();
//                System.out.print(" \n\n\n");
//                for (int i = 0; i < c.getColumnCount(); i++) {
//                    System.out.print(c.getColumnName(i)+" , ");
//                }
//                System.out.print("; \n\n\n");
//                break;
//            } while (c.moveToNext());
//        }

//        VideoView v = null;
//        MediaMetadataRetriever m = new MediaMetadataRetriever();
//        m.setDataSource();


    }

    private void test3(String path) {
        MediaExtractor extractor = new MediaExtractor();
        try {
            MediaMetadataRetriever m = new MediaMetadataRetriever();
            m.setDataSource(path);
//            Log.i(TAG, "test3: METADATA_KEY_DATE = " + m.extractMetadata(MediaMetadataRetriever.DATA));

            Log.i(TAG, "test3: METADATA_KEY_BITRATE = " + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
            Log.i(TAG, "test3: METADATA_KEY_DATE = " + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DATE));
            Log.i(TAG, "test3: METADATA_KEY_DURATION = " + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            Log.i(TAG, "test3: METADATA_KEY_MIMETYPE = " + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE));
            Log.i(TAG, "test3: METADATA_KEY_TITLE = " + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            Log.i(TAG, "test3: METADATA_KEY_NUM_TRACKS = " + m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_NUM_TRACKS));

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        try {
            extractor.setDataSource(path);
            int tarkCount = extractor.getTrackCount();
            for (int i = 0; i < tarkCount; i++) {
                MediaFormat mediaFormat = extractor.getTrackFormat(i);
                if (mediaFormat != null && mediaFormat.getString(MediaFormat.KEY_MIME).startsWith("video/")) {
//                    Log.i(TAG, "test3: KEY_BIT_RATE = " + mediaFormat.getInteger(MediaFormat.KEY_BIT_RATE));
//                    Log.i(TAG, "test3: KEY_FRAME_RATE = " + mediaFormat.getFloat(MediaFormat.KEY_FRAME_RATE));
//                    Log.i(TAG, "test3: KEY_WIDTH = " + mediaFormat.getInteger(MediaFormat.KEY_WIDTH));
//                    Log.i(TAG, "test3: KEY_HEIGHT = " + mediaFormat.getInteger(MediaFormat.KEY_HEIGHT));
                    Field f = MediaFormat.class.getDeclaredField("mMap");
                    f.setAccessible(true);
                    Object o = f.get(mediaFormat);
                    Log.i(TAG, "test3: path = " + path + "\n" + o);
                }
            }
//            extractor.advance();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    private void test2() {

        BaiduMapImp.getInstance().init(getApplicationContext());
//        CommonUtil.getLngAndLatWithNetwork();
        CommonUtil.getLngAndLat();
    }

    private void test1() {
//创建一个上游 Observable：
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建一个下游 Observer
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }


        };
        //建立连接
        observable.subscribe(observer);
//
//        作者：Season_zlc
//        链接：https://www.jianshu.com/p/464fa025229e
//        來源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


    }


    public static void main(String[] agrs) {

        while (true) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("请输入一个正整数！");
            int count;
            try {

                count = scanner.nextInt();
                if(count < 0){
                    throw new IllegalStateException();
                }
            }catch (Exception ex){
                ex.printStackTrace();
                System.out.println("输入错误结束！");
                break;
            }

            double sum = 1.0;

            for (int i = 1; i <= count; i++) {

                sum = sum * 1.1;

            }

            System.out.printf("运算结果为 sum = %.4f \n",sum);
        }


    }

}
