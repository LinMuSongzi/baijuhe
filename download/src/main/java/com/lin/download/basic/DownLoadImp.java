package com.lin.download.basic;

import android.content.Context;

import com.lin.download.basic.provide.DownLoadProvider;
import com.lin.download.basic.provide.SupportProvide;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Queue;

import y.com.sqlitesdk.framework.entity.respone.QuerySingleRespone;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadImp implements DownLoadExpress {


    Context context;

    public DownLoadImp(Context context) {
        this.context = context;
    }



    @Override
    public BaseDownloadTask download(IBasicInfo info) {
        BaseDownloadTask baseDownloadTask;
        final int i = (baseDownloadTask = FileDownloader.getImpl().
                create(info.getDownLoadUrl()).setListener(new SimpleFileListenerImp() {

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);
            }
        }).setPath(info.getSavePath()).setSyncCallback(true).setAutoRetryTimes(AUTO_RETRY_TIMES)).start();
        info.setDownLoadId(String.valueOf(i));
        return baseDownloadTask;
    }


}
