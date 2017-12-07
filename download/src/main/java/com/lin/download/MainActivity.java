package com.lin.download;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lin.download.basic.provide.DownLoadProvider;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.yeyuanyuan.web.StrEntity;
import com.yeyuanyuan.web.Zygote;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    public Toast toast;

    BaseDownloadTask baseDownloadTask;
    @Bind(R.id.id_progressBar)
    SeekBar id_progressBar;
    @Bind(R.id.id_start_btn)
    View id_start_btn;
    @Bind(R.id.id_puase_btn)
    View id_puase_btn;
    @Bind(R.id.id_lenght)
    TextView id_lenght;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        FileDownloader.setup(this);
        baseDownloadTask = FileDownloader.
                getImpl().
                create("http://gdown.baidu.com/data/wisegame/f28ba370126f3605/QQ_744.apk").
                setPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "QQ_744.apk").
                setWifiRequired(true).
                setListener(new FileDownloadLargeFileListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        Log.i(TAG, "pending: soFarBytes = " + soFarBytes + "  totalBytes = " + totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        Log.i(TAG, "progress: soFarBytes = " + soFarBytes + "  totalBytes = " + totalBytes);
//                toastShow(soFarBytes);
                        id_progressBar.setProgress((int) (soFarBytes * 1f/totalBytes * 100.2f));
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        Log.i(TAG, "paused: soFarBytes = " + soFarBytes + "  totalBytes = " + totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.i(TAG, "completed: ");
                        id_start_btn.setEnabled(true);
                        id_puase_btn.setEnabled(false);
                        toastShow("下载完成");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.i(TAG, "error: ");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.i(TAG, "warn: ");
                    }
                });
        id_puase_btn.setEnabled(false);
        test();


//        getContentResolver().delete(DownLoadProvider.CONTENT_DELETE_URI,null,null);


    }

    private void test() {
        EventBus.getDefault().register(this);
        Zygote.createGet(StrEntity.class,"http://www.baidu.com",null).asyncExecute();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(StrEntity strEntity){
        if(strEntity.getRequet()!=null){

        }
    }


    public void onStartClick(View view) {
        if(baseDownloadTask.isUsing()){
            if(baseDownloadTask.reuse()){
                toastShow("重新开始");
                view.performClick();
            }
        }else {
            toastShow("开始");
            baseDownloadTask.start();
            view.setEnabled(false);
            id_puase_btn.setEnabled(true);
        }
    }

    public void onPauseClick(View view) {
        toastShow("暂停");
        baseDownloadTask.pause();
        view.setEnabled(false);
        id_start_btn.setEnabled(true);
    }

    private void toastShow(String s) {
        toast.setText(s);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
